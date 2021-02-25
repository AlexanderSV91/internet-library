package com.faceit.example.service.impl.redis;

import com.faceit.example.model.redis.BookRedis;
import com.faceit.example.repository.redis.BookRedisRepository;
import com.faceit.example.service.redis.BookRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookRedisServiceImpl implements BookRedisService {

    private final BookRedisRepository bookRedisRepository;

    @Override
    public BookRedis findById(Long id) {
        return bookRedisRepository.findById(id);
    }

    @Override
    public void save(BookRedis book) {
        bookRedisRepository.save(book);
    }

    @Override
    public void delete(long id) {
        bookRedisRepository.delete(id);
    }
}
