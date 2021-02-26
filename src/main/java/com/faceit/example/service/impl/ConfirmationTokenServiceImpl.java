package com.faceit.example.service.impl;

import com.faceit.example.exception.ResourceNotFoundException;
import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.repository.postgre.ConfirmationTokenRepository;
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

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
    private final TokenRedisService tokenRedisService;
    private final UserService userService;

    @Override
    public void addConfirmationToken(UsersRecord newUser) {
        UsersRecord user = userService.addUser(newUser);
        ConfirmationTokensRecord tokensRecord =
                confirmationTokenRepository.save(preparingToConfirmationToken(user.getId()));
        try {
            emailSenderService.sendActiveEmail(user, tokensRecord.getRedisKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenStatus findByToken(String token) {
        ConfirmationTokensRecord confirmationToken = confirmationTokenRepository.findByToken(token);

        switch (TokenStatus.valueOf(confirmationToken.getStatus())) {
            case PENDING: {
                if (confirmationToken.getIssuedDate().isAfter(LocalDateTime.now().minusDays(2))) {
                    UsersRecord user = userService.getUserById(confirmationToken.getUserId());
                    user.setEnabled(true);
                    userService.updateUserById(user, user.getId());

                    confirmationToken.setStatus(VERIFIED.name());
                    confirmationToken.setRedisKey("");
                    updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
                    return VERIFIED;
                } else {
                    confirmationToken.setStatus(EXPIRED.name());
                    confirmationToken.setRedisKey("");
                    updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
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

    @Override
    public ConfirmationTokensRecord getConfirmationTokenById(long id) {
        return confirmationTokenRepository.findById(id);
    }

    @Override
    public boolean existsByToken(String token) {
        return confirmationTokenRepository.existsByRedisKey(token);
    }

    @Override
    public ConfirmationTokensRecord updateConfirmationTokenById(
            ConfirmationTokensRecord updateConfirmationToken, long id) {
        ConfirmationTokensRecord confirmationToken = getConfirmationTokenById(id);
        if (confirmationToken != null) {
            updateConfirmationToken.setId(id);
        } else {
            throw new ResourceNotFoundException("exception.notFound");
        }
        return confirmationTokenRepository.update(updateConfirmationToken);
    }

    @Override
    public List<ConfirmationTokensRecord> getAllConfirmationToken() {
        return confirmationTokenRepository.findAll();
    }

    private ConfirmationTokensRecord preparingToConfirmationToken(long userId) {
        ConfirmationTokensRecord confirmationToken = new ConfirmationTokensRecord();
        confirmationToken.setUserId(userId);
        confirmationToken.setIssuedDate(LocalDateTime.now());
        confirmationToken.setStatus(PENDING.name());

        String token = UUID.randomUUID().toString();
        while (null != tokenRedisService.findByKey(token)) {
            token = UUID.randomUUID().toString();
        }
        confirmationToken.setRedisKey(token);
        tokenRedisService.save(token, token);
        return confirmationToken;
    }
}
