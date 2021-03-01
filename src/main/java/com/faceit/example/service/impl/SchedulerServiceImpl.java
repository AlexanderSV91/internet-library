package com.faceit.example.service.impl;

import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.model.redis.ConfirmationToken;
import com.faceit.example.service.EmailSenderService;
import com.faceit.example.service.SchedulerService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.service.redis.TokenRedisService;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private static final String CRON = "0 0 */1 * * *";

    private final EmailSenderService emailSenderService;
    private final TokenRedisService tokenRedisService;
    private final UserService userService;

    @Override
    @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        Iterable<ConfirmationToken> confirmationTokens = tokenRedisService.findAll();
        confirmationTokens.forEach(confirmationToken -> {
            if (TokenStatus.PENDING == confirmationToken.getStatus()) {
                LocalDateTime issuedDate = confirmationToken.getIssuedDate();
                LocalDateTime currentDate = LocalDateTime.now();
                Duration duration = Duration.between(currentDate, issuedDate.plusDays(2));

                if (duration.toMinutes() <= 60 && duration.toMinutes() >= 0) {
                    try {
                        UsersRecord userRecord = userService.getUserById(confirmationToken.getUserId());
                        emailSenderService.sendActiveEmail(userRecord, confirmationToken.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (duration.toMinutes() < 0) {
                    confirmationToken.setStatus(TokenStatus.EXPIRED);
                    tokenRedisService.save(confirmationToken);
                }
            }
        });
    }
}
