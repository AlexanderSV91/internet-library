package com.faceit.example.controller;

import com.faceit.example.model.redis.BookRedis;
import com.faceit.example.service.redis.BookRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/v1/book-redis"})
@RequiredArgsConstructor
public class BookRedisControllerRest {

    private final BookRedisService bookRedisService;

    @GetMapping("/{id}")
    public BookRedis findById(@PathVariable Long id) {
        return bookRedisService.findById(id);
    }

    @PostMapping
    public void save(BookRedis book) {
        bookRedisService.save(book);
    }

    @DeleteMapping
    public void delete(long id) {
        bookRedisService.delete(id);
    }
}
