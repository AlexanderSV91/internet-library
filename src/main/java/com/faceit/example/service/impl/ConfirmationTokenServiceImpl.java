package com.faceit.example.service.impl;

import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.service.ConfirmationTokenService;
import com.faceit.example.tables.ConfirmationTokens;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Override
    public void addConfirmationToken(UsersRecord user) {

    }

    @Override
    public TokenStatus findByToken(String token) {
        return null;
    }

    @Override
    public ConfirmationTokens getConfirmationTokenById(long id) {
        return null;
    }

    @Override
    public boolean existsByToken(String token) {
        return false;
    }

    @Override
    public ConfirmationTokens updateConfirmationTokenById(ConfirmationTokens updateConfirmationToken, long id) {
        return null;
    }

    @Override
    public List<ConfirmationTokens> getAllConfirmationToken() {
        return null;
    }
}
