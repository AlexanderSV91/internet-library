package com.faceit.example.service.impl;

import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.service.ConfirmationTokenService;
import com.faceit.example.service.EmailSenderService;
import com.faceit.example.service.SchedulerService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.service.redis.TokenRedisService;
import com.faceit.example.tables.records.ConfirmationTokensRecord;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private static final String CRON = "0 0 */1 * * *";

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final TokenRedisService tokenRedisService;
    private final UserService userService;

    @Override
    @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        List<ConfirmationTokensRecord> confirmationTokens =
                confirmationTokenService.getAllConfirmationToken();
        for (ConfirmationTokensRecord confirmationToken : confirmationTokens) {
            TokenStatus tokenStatus = TokenStatus.valueOf(confirmationToken.getStatus());

            if (tokenStatus == TokenStatus.PENDING) {
                LocalDateTime issuedDate = confirmationToken.getIssuedDate();
                LocalDateTime currentDate = LocalDateTime.now();
                Duration duration = Duration.between(currentDate, issuedDate.plusDays(2));

                if (duration.toMinutes() <= 60 && duration.toMinutes() >= 0) {
                    try {
                        UsersRecord userRecord = userService.getUserById(confirmationToken.getUserId());
                        String token = tokenRedisService.findByKey(confirmationToken.getRedisKey());
                        if (null != token && !"".equals(token)) {
                            emailSenderService.sendActiveEmail(userRecord, token);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (duration.toMinutes() < 0) {
                    confirmationToken.setRedisKey("");
                    confirmationToken.setStatus(TokenStatus.EXPIRED.name());
                    confirmationTokenService
                            .updateConfirmationTokenById(confirmationToken, confirmationToken.getId());
                }
            }
        }
    }
}
