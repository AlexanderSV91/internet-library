package com.faceit.example.model.redis;

import com.faceit.example.model.enumeration.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "confirmation_token", timeToLive = 1_800L)
public class ConfirmationToken {

    @Id
    private String id;
    private long userId;
    private TokenStatus status;
    private LocalDateTime issuedDate;
}
