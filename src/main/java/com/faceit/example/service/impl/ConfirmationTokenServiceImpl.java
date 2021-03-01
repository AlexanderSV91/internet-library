package com.faceit.example.service.impl;

import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.model.redis.ConfirmationToken;
import com.faceit.example.service.ConfirmationTokenService;
import com.faceit.example.service.EmailSenderService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.service.redis.TokenRedisService;
import com.faceit.example.tables.records.ConfirmationTokensRecord;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.faceit.example.model.enumeration.TokenStatus.*;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final EmailSenderService emailSenderService;
    private final TokenRedisService tokenRedisService;
    private final UserService userService;

    @Override
    public void addConfirmationToken(UsersRecord newUser) {
        UsersRecord user = userService.addUser(newUser);
        ConfirmationToken confirmationToken = preparingToConfirmationToken(user.getId());
        tokenRedisService.save(confirmationToken);

        try {
            emailSenderService.sendActiveEmail(user, confirmationToken.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenStatus findByToken(String token) {
        ConfirmationToken confirmationToken = tokenRedisService.findByKey(token);
        switch (confirmationToken.getStatus()) {
            case PENDING: {
                if (confirmationToken.getIssuedDate().isAfter(LocalDateTime.now().minusDays(2))) {
                    UsersRecord user = userService.getUserById(confirmationToken.getUserId());

                    user.setEnabled(true);
                    userService.updateUserById(user, user.getId());

                    confirmationToken.setStatus(VERIFIED);
                    tokenRedisService.save(confirmationToken);
                    return VERIFIED;
                } else {
                    confirmationToken.setStatus(EXPIRED);
                    tokenRedisService.save(confirmationToken);
                    return EXPIRED;
                }
            }
            case VERIFIED: {
                return VERIFIED;
            }
            default:
                return EXPIRED;
        }
    }

    private ConfirmationToken preparingToConfirmationToken(long userId) {
        String token = UUID.randomUUID().toString();
        while (tokenRedisService.existsById(token)) {
            token = UUID.randomUUID().toString();
        }
        return ConfirmationToken.builder()
                .id(token)
                .userId(userId)
                .status(PENDING)
                .issuedDate(LocalDateTime.now())
                .build();
    }
}
