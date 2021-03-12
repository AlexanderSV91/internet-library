package com.faceit.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadConfig {

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_NUM_POOL_SIZE = 4;
    private static final int QUEUE_CAPACITY = 50;

    @Bean(name = "threadPoolTaskExecutorSendMail")
    public Executor asyncExecutor0() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_NUM_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("CustomExecutorSendMail::");
        executor.initialize();
        return executor;
    }
}
