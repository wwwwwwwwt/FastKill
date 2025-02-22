package org.ztw.fastkill.application.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.ztw.fastkill.application.service.RedisService;

import javax.annotation.Resource;

@Component
public class RedisServiceImpl implements RedisService {

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
