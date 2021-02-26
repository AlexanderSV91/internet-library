package com.faceit.example.service.impl.redis;

import com.faceit.example.repository.redis.TokenRedisRepository;
import com.faceit.example.service.redis.TokenRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenRedisServiceImpl implements TokenRedisService {

    private final TokenRedisRepository tokenRedisRepository;

    @Override
    public String findByKey(String key) {
        return tokenRedisRepository.findByKey(key);
    }

    @Override
    public void save(String key, String value) {
        tokenRedisRepository.save(key, value);
    }

    @Override
    public void delete(String id) {
        tokenRedisRepository.delete(id);
    }
}
