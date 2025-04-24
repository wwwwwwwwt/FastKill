package org.ztw.fastkill.application.cache.service.activity;

import org.ztw.fastkill.application.cache.model.SeckillBusinessCache;
import org.ztw.fastkill.application.cache.service.common.SeckillCacheService;
import org.ztw.fastkill.domain.model.SeckillActivity;

public interface SeckillActivityCacheService extends SeckillCacheService {
    SeckillBusinessCache<SeckillActivity> getCachedActivity(Long activityId, Long version);
    SeckillBusinessCache<SeckillActivity> tryUpdateSeckillActivityCacheByLock(Long activityId);
}
