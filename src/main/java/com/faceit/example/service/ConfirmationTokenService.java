package com.faceit.example.service;


import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.tables.records.UsersRecord;

public interface ConfirmationTokenService {

    void addConfirmationToken(UsersRecord newUser);

    TokenStatus findByToken(String token);
}
