package com.faceit.example.service;


import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.tables.ConfirmationTokens;
import com.faceit.example.tables.records.UsersRecord;

import java.util.List;

public interface ConfirmationTokenService {

    void addConfirmationToken(UsersRecord user);

    TokenStatus findByToken(String token);

    ConfirmationTokens getConfirmationTokenById(long id);

    boolean existsByToken(String token);

    ConfirmationTokens updateConfirmationTokenById(ConfirmationTokens updateConfirmationToken, long id);

    List<ConfirmationTokens> getAllConfirmationToken();
}
