package com.faceit.example.model.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("BookRedis")
public class BookRedis implements Serializable {

    @Id
    private long id;
    private String name;
    private String bookCondition;
    private String description;
}
