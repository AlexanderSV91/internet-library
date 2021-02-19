package com.faceit.example.service.impl;

import com.faceit.example.service.postgre.NumberAuthorizationService;
import com.faceit.example.service.postgre.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final UserService userService;
    private final NumberAuthorizationService numberAuthorizationService;

    /*@Transactional
    public void loginSucceeded(String username) {
        NumberAuthorization numberAuthorization = userService
                .findUserByUserName(username).getNumberAuthorization();
        numberAuthorization.setLastAuthorizationDate(LocalDateTime.now());
        numberAuthorizationService.updateNumberAuthorizationById(numberAuthorization);
    }

    @Transactional
    public void loginFailed(String username) {
        User user = userService.findUserByUserName(username);
        NumberAuthorization numberAuthorization = user.getNumberAuthorization();
        int numberOfAttempts = numberAuthorization.getQuantity();
        numberOfAttempts--;
        if (numberOfAttempts <= 0) {
            user.setEnabled(false);
            userService.updateUserById(user, user.getId());
        } else {
            numberAuthorization.setQuantity(numberOfAttempts);
            numberAuthorizationService.updateNumberAuthorizationById(numberAuthorization);
        }
    }*/
}
