
package org.ztw.fastkill.infrastructure.cache.local;

public interface LocalCacheService<K, V> {

    void put(K key, V value);

    V getIfPresent(Object key);
}
