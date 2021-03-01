package com.faceit.example.service.redis;

import com.faceit.example.model.redis.ConfirmationToken;

public interface TokenRedisService {

    Iterable<ConfirmationToken> findAll();

    ConfirmationToken findByKey(String key);

    void save(ConfirmationToken value);

    boolean existsById(String id);
}
