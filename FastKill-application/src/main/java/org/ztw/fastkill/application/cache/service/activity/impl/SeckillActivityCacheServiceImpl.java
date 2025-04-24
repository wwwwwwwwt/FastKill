package org.ztw.fastkill.application.cache.service.activity.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.ztw.fastkill.application.builder.SeckillActivityBuilder;
import org.ztw.fastkill.application.cache.model.SeckillBusinessCache;
import org.ztw.fastkill.application.cache.service.activity.SeckillActivityCacheService;
import org.ztw.fastkill.application.cache.service.activity.SeckillActivityListCacheService;
import org.ztw.fastkill.common.constants.SeckillConstants;
import org.ztw.fastkill.common.time.SystemClock;
import org.ztw.fastkill.domain.model.SeckillActivity;
import org.ztw.fastkill.domain.repository.SecKillActivityRepository;
import org.ztw.fastkill.infrastructure.cache.distribute.DistributedCacheService;
import org.ztw.fastkill.infrastructure.cache.local.LocalCacheService;
import org.ztw.fastkill.infrastructure.lock.DistributedLock;
import org.ztw.fastkill.infrastructure.lock.factoty.DistributedLockFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.ztw.fastkill.common.constants.SeckillConstants.SECKILL_ACTIVITY_CACHE_KEY;

@Slf4j
@Service
public class SeckillActivityCacheServiceImpl implements SeckillActivityCacheService {

    //分布式锁的key
    private static final String SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY = "SECKILL_ACTIVITIE_UPDATE_CACHE_LOCK_KEY_";
    //本地锁
    private final Lock localCacheUpdateloock = new ReentrantLock();
    @Resource
    private LocalCacheService<Long, SeckillBusinessCache<SeckillActivity>> localCacheService;
    @Resource
    private DistributedCacheService distributedCacheService;
    @Resource
    private SecKillActivityRepository seckillActivityRepository;
    @Resource
    private DistributedLockFactory distributedLockFactory;

    @Override
    public String buildCacheKey(Object key) {
        return SECKILL_ACTIVITY_CACHE_KEY + key;
    }

    @Override
    public SeckillBusinessCache<SeckillActivity> getCachedActivity(Long activityId, Long version) {
        log.info("SeckillActivityCache|读取本地缓存|activityId:{}",activityId);
        SeckillBusinessCache<SeckillActivity> activityCache = localCacheService.getIfPresent(activityId);
        if(activityCache != null){
            if(version == null){
                log.info("SeckillActivityCache|命中本地缓存|activityId{}", activityId);
                return activityCache;
            }

            if(version.compareTo(activityCache.getVersion()) <= 0){
                log.info("SeckillActivityCache|命中本地缓存|activityId{}", activityId);
                return activityCache;
            }
            if(version.compareTo(activityCache.getVersion()) > 0){
                log.info("SeckillActivityCache|未命中本地缓存|activityId{}，更新", activityId);
                getDistributedCache(activityId);
            }
        }

        return getDistributedCache(activityId);
    }
    private SeckillBusinessCache<SeckillActivity> getDistributedCache(Long activityId) {
        log.info("SeckillActivityCache|读取分布式缓存|activityId:{}",activityId);
        SeckillBusinessCache<SeckillActivity> activityCache = SeckillActivityBuilder.getSeckillBusinessCache(distributedCacheService.getObject(buildCacheKey(activityId)), SeckillActivity.class);
        if(activityCache == null){
            activityCache = tryUpdateSeckillActivityCacheByLock(activityId);
        }
        if(activityCache != null && !activityCache.isRetryLater()){
            if(localCacheUpdateloock.tryLock()){
                try {
                    localCacheService.put(activityId, activityCache);
                    log.info("SeckillActivityCache|本地缓存已经更新|activityId:{}", activityId);
                }finally {
                    localCacheUpdateloock.unlock();
                }
            }
        }
        return activityCache;
    }
    @Override
    public SeckillBusinessCache<SeckillActivity> tryUpdateSeckillActivityCacheByLock(Long activityId) {
        log.info("SeckillActivityCache|更新分布式缓存|activityId:{}", activityId);
        DistributedLock lock = distributedLockFactory.getDistributedLock(SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY.concat(String.valueOf(activityId)));
        try{
            boolean isSuccessLock = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!isSuccessLock){
                return new SeckillBusinessCache<SeckillActivity>().retryLater();
            }
            SeckillActivity seckillActivity = seckillActivityRepository.getSecKillActivityById(activityId);
            SeckillBusinessCache<SeckillActivity> seckillActivityCache;
            if(seckillActivity == null){
                seckillActivityCache = new SeckillBusinessCache<SeckillActivity>().notExist();
            } else {
                seckillActivityCache = new SeckillBusinessCache<SeckillActivity>().with(seckillActivity).withVersion(SystemClock.millisClock().now());
            }
            distributedCacheService.put(buildCacheKey(activityId), JSON.toJSONString(seckillActivityCache), SeckillConstants.FIVE_MINUTES);
            log.info("SeckillActivityCache|分布式缓存已经更新|activityId:{}", activityId);
            return seckillActivityCache;
        } catch (InterruptedException e) {
            log.info("SeckillActivitesCache|更新分布式缓存失败|{}", activityId);
            return new SeckillBusinessCache<SeckillActivity>().retryLater();
        } finally {
            lock.unlock();
        }
    }
}
