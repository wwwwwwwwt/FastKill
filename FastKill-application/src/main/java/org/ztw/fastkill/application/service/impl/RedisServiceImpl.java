package org.ztw.fastkill.application.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.ztw.fastkill.application.service.RedisService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImpl implements RedisService {

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
