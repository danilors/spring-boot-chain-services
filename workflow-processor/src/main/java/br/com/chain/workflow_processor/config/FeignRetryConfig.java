package br.com.chain.workflow_processor.config;

import br.com.chain.workflow_processor.client.reactive.AddressReactiveClient;
import br.com.chain.workflow_processor.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.FallbackFactory;
import reactivefeign.spring.config.ReactiveRetryPolicies;
import reactor.core.publisher.Mono;

import static reactivefeign.retry.BasicReactiveRetryPolicy.retry;

@Configuration
@Slf4j
public class FeignRetryConfig {

    @Bean
    public ReactiveRetryPolicies retryPolicies() {
        return new ReactiveRetryPolicies.Builder()
                .retryOnNext(retry(3)).build();
    }

    @Bean
    public AddressFallbackFactory addressFallbackFactory() {
        return new AddressFallbackFactory();
    }

    public static class AddressFallbackFactory implements FallbackFactory<AddressReactiveClient> {

        @Override
        public AddressReactiveClient apply(Throwable cause) {
            return id -> {
                log.warn("Fallback called: {}", cause.getMessage());
                Address fallbackAddress = Address.defaulAddres();
                return Mono.just(fallbackAddress);
            };
        }
    }
}