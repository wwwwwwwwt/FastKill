package org.ztw.fastkill.application.cache.model;


import org.ztw.fastkill.application.cache.model.common.SeckillCommonCache;

/**
 * @author binghe(微信 : hacker_binghe)
 * @version 1.0.0
 * @description 业务数据缓存
 * @github https://github.com/binghe001
 * @copyright 公众号: 冰河技术
 */
public class SeckillBusinessCache<T> extends SeckillCommonCache {

    private T data;

    public SeckillBusinessCache<T> with(T data){
        this.data = data;
        this.exist = true;
        return this;
    }

    public SeckillBusinessCache<T> withVersion(Long version){
        this.version = version;
        return this;
    }

    public SeckillBusinessCache<T> retryLater(){
        this.retryLater = true;
        return this;
    }

    public SeckillBusinessCache<T> notExist(){
        this.exist = false;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
