package com.faceit.example.service.redis;

public interface TokenRedisService {

    String findByKey(String key);

    void save(String key, String value);

    void delete(String id);
}
