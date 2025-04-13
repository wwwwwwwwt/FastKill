package org.ztw.fastkill.application.cache.service.activity;

import org.ztw.fastkill.application.cache.model.SeckillBusinessCache;
import org.ztw.fastkill.application.cache.service.common.SeckillCacheService;
import org.ztw.fastkill.domain.model.SeckillActivity;

import java.util.List;

public interface SeckillActivityListCacheService extends SeckillCacheService {

    /**
     * 增加二级缓存的根据状态获取活动列表
     */
    SeckillBusinessCache<List<SeckillActivity>> getCachedActivities(Integer status, Long version);

    /**
     * 更新缓存数据
     */
    SeckillBusinessCache<List<SeckillActivity>> tryUpdateSeckillActivityCacheByLock(Integer status);
}
