package com.faceit.example.service.redis;

import com.faceit.example.model.redis.BookRedis;

public interface BookRedisService {

    BookRedis findById(Long id);

    void save(BookRedis book);

    void delete(long id);
}
