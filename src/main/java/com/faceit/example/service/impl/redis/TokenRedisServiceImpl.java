package com.faceit.example.service.impl.redis;

import com.faceit.example.exception.ResourceNotFoundException;
import com.faceit.example.model.redis.ConfirmationToken;
import com.faceit.example.repository.redis.TokenRedisRepository;
import com.faceit.example.service.redis.TokenRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenRedisServiceImpl implements TokenRedisService {

    private final TokenRedisRepository tokenRedisRepository;

    @Override
    public Iterable<ConfirmationToken> findAll() {
        return tokenRedisRepository.findAll();
    }

    @Override
    public ConfirmationToken findByKey(String key) {
        Optional<ConfirmationToken> optional = tokenRedisRepository.findById(key);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ResourceNotFoundException("not found");
    }

    @Override
    public void save(ConfirmationToken confirmationToken) {
        tokenRedisRepository.save(confirmationToken);
    }

    @Override
    public boolean existsById(String id) {
        return tokenRedisRepository.existsById(id);
    }
}
