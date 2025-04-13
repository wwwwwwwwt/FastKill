package org.ztw.fastkill.application.cache.service.common;

public interface SeckillCacheService {
    /**
     * 构建缓存的key
     */
    String buildCacheKey(Object key);
}
