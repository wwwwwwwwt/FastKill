
package org.ztw.fastkill.application.builder.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.ztw.fastkill.application.cache.model.SeckillBusinessCache;

import java.util.List;


public class SeckillCommonBuilder {
    /**
     * Json泛型化处理
     */
    public static <T> SeckillBusinessCache<T> getSeckillBusinessCache(Object object, Class<T> clazz){
        if (object == null){
            return null;
        }
        return JSON.parseObject(object.toString(), new TypeReference<SeckillBusinessCache<T>>(clazz){});
    }

    /**
     * Json泛型化处理
     */
    public static <T> SeckillBusinessCache<List<T>> getSeckillBusinessCacheList(Object object, Class<T> clazz){
        if (object == null){
            return null;
        }
        return JSON.parseObject(object.toString(), new TypeReference<SeckillBusinessCache<List<T>>>(clazz){});
    }
}
