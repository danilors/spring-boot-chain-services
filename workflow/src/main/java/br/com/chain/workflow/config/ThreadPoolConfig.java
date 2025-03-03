package br.com.chain.workflow.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(40);
        executor.setMaxPoolSize(60);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Executor-");
        executor.initialize();
        return executor;
    }

}
