package org.ztw.fastkill.application.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void set(String key, Object value);
    void set(String key, Object value, long expire, TimeUnit timeUnit);
    Object get(String key);
}
