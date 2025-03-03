package br.com.chain.workflow.config;


import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RetryConfiguration {

//    @Bean
    public Retryer retryer() {
        return new Retryer.Default(500, 2000, 3);
    }
}

