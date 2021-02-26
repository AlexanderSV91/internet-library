package com.faceit.example.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {

    private final static long TIMEOUT_SECONDS = 900L;

    private final RedisTemplate<String, String> redisTemplate;

    public String findByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public void delete(String id) {
        redisTemplate.delete(id);
    }
}
