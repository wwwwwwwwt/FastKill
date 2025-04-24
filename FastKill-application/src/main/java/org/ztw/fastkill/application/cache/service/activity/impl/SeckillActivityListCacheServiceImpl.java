package org.ztw.fastkill.application.cache.service.activity.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ztw.fastkill.application.builder.SeckillActivityBuilder;
import org.ztw.fastkill.application.cache.model.SeckillBusinessCache;
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

import static org.ztw.fastkill.common.constants.SeckillConstants.SECKILL_ACTIVITIES_CACHE_KEY;

@Service
@Slf4j
public class SeckillActivityListCacheServiceImpl implements SeckillActivityListCacheService {

    //分布式锁的key
    private static final String SECKILL_ACTIVITES_UPDATE_CACHE_LOCK_KEY = "SECKILL_ACTIVITIES_UPDATE_CACHE_LOCK_KEY_";
    //本地锁
    private final Lock localCacheUpdatelock = new ReentrantLock();
    @Resource
    private LocalCacheService<Long, SeckillBusinessCache<List<SeckillActivity>>> localCacheService;
    @Resource
    private DistributedCacheService distributedCacheService;
    @Resource
    private SecKillActivityRepository seckillActivityRepository;
    @Resource
    private DistributedLockFactory distributedLockFactory;
    @Override
    public SeckillBusinessCache<List<SeckillActivity>> getCachedActivities(Integer status, Long version) {
        log.info("SeckillActivitesCache|读取本地缓存|{}", status);
        SeckillBusinessCache<List<SeckillActivity>> seckillActivitiyListCache = localCacheService.getIfPresent(status.longValue());
        if(seckillActivitiyListCache != null){
            if(version == null){
                log.info("SeckillActivitesCache|命中本地缓存|{}", status);
                return seckillActivitiyListCache;
            }

            if(version.compareTo(seckillActivitiyListCache.getVersion()) <= 0){
                log.info("SeckillActivitesCache|命中本地缓存|{}", status);
                return seckillActivitiyListCache;
            }

            if(version.compareTo(seckillActivitiyListCache.getVersion()) > 0){
                log.info("SeckillActivitesCache|未命中本地缓存|{}，更新", status);
                getDistributedCache(status);
            }
        }
        return getDistributedCache(status);
    }
    /**
     * 获取分布式缓存中的数据
     */
    private SeckillBusinessCache<List<SeckillActivity>> getDistributedCache(Integer status) {
        log.info("SeckillActivitesCache|读取分布式缓存|{}", status);
        SeckillBusinessCache<List<SeckillActivity>> seckillActivitiyListCache = SeckillActivityBuilder.getSeckillBusinessCacheList(distributedCacheService.getObject(buildCacheKey(status)),  SeckillActivity.class);
        if (seckillActivitiyListCache == null){
            seckillActivitiyListCache = tryUpdateSeckillActivityCacheByLock(status);
        }
        if (seckillActivitiyListCache != null && !seckillActivitiyListCache.isRetryLater()){
            if (localCacheUpdatelock.tryLock()){
                try {
                    localCacheService.put(status.longValue(), seckillActivitiyListCache);
                    log.info("SeckillActivitesCache|本地缓存已经更新|{}", status);
                }finally {
                    localCacheUpdatelock.unlock();
                }
            }
        }
        return seckillActivitiyListCache;
    }
    @Override
    public SeckillBusinessCache<List<SeckillActivity>> tryUpdateSeckillActivityCacheByLock(Integer status) {
        log.info("SeckillActivitesCache|更新分布式缓存|{}", status);
        DistributedLock lock = distributedLockFactory.getDistributedLock(SECKILL_ACTIVITES_UPDATE_CACHE_LOCK_KEY.concat(String.valueOf(status)));
        try{
            boolean isSuccessLock = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!isSuccessLock){
                return new SeckillBusinessCache<List<SeckillActivity>>().retryLater();
            }
            List<SeckillActivity> seckillActivityList = seckillActivityRepository.getSeckillActivityList(status);
            SeckillBusinessCache<List<SeckillActivity>> seckillActivitiyListCache;
            if (seckillActivityList == null){
                seckillActivitiyListCache = new SeckillBusinessCache<List<SeckillActivity>>().notExist();
            }else {
                seckillActivitiyListCache = new SeckillBusinessCache<List<SeckillActivity>>().with(seckillActivityList).withVersion(SystemClock.millisClock().now());
            }
            distributedCacheService.put(buildCacheKey(status), JSON.toJSONString(seckillActivitiyListCache), SeckillConstants.FIVE_MINUTES);
            log.info("SeckillActivitesCache|分布式缓存已经更新|{}", status);
            return seckillActivitiyListCache;
        } catch (InterruptedException e) {
            log.info("SeckillActivitesCache|更新分布式缓存失败|{}", status);
            return new SeckillBusinessCache<List<SeckillActivity>>().retryLater();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String buildCacheKey(Object key) {
        return SECKILL_ACTIVITIES_CACHE_KEY + key;
    }
}
