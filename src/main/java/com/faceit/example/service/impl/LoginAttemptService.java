package com.faceit.example.service.impl;

import com.faceit.example.service.postgre.NumberAuthorizationService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.NumberAuthorizationsRecord;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final UserService userService;
    private final NumberAuthorizationService numberAuthorizationService;

    @Transactional
    public void loginSucceeded(String username) {
        log.error(username);
        /*UsersRecord userRecord = userService.findUserByUserName(username);
        NumberAuthorizationsRecord numberAuthorization =
                numberAuthorizationService.getById(userRecord.getId());
        numberAuthorization.setLastAuthorizationDate(LocalDateTime.now());
        numberAuthorizationService.updateById(numberAuthorization);*/
    }

    @Transactional
    public void loginFailed(String username) {
        UsersRecord userRecord = userService.findUserByUserName(username);
        NumberAuthorizationsRecord numberAuthorization =
                numberAuthorizationService.getById(userRecord.getId());
        int numberOfAttempts = numberAuthorization.getQuantity();
        numberOfAttempts--;
        if (numberOfAttempts <= 0) {
            userRecord.setEnabled(false);
            userService.updateUserById(userRecord, userRecord.getId());
        } else {
            numberAuthorization.setQuantity(numberOfAttempts);
            numberAuthorizationService.updateById(numberAuthorization);
        }
    }
}
