package com.faceit.example.repository.redis;

import com.faceit.example.model.redis.BookRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRedisRepository {

    private final RedisTemplate<Long, BookRedis> redisTemplate;

    public BookRedis findById(Long id) {
        return redisTemplate.opsForValue().get(id);
    }

    public void save(BookRedis book) {
        redisTemplate.opsForValue().set(book.getId(), book);
    }

    public void delete(long id) {
        redisTemplate.delete(id);
    }
}
