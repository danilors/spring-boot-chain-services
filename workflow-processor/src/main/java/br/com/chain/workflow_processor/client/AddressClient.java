package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AddressClient extends AbstractClient {

    private final Cache addressCache;

    @Autowired
    public AddressClient(WebClient.Builder webClientBuilder,
                         @Value("${address.api.baseUrl}") String baseUrl,
                         @Value("${address.api.path}") String path,
                         @Value("${address.api.maxRetry}") Integer maxRetry,
                         @Value("${address.api.maxRetryInterval}") Integer maxRetryInterval,
                         CacheManager cacheManager
    ) {
        this.path = path;
        this.maxRetry = maxRetry;
        this.maxRetryInterval = maxRetryInterval;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.addressCache = cacheManager.getCache("addresses"); // Assuming a cache named "addresses"

    }

    public Mono<Address> getAddressById(Integer id) {
        Instant startTime = Instant.now();

        log.debug("Starting getAddressById for id: {}", id);

        Address cachedAddress = addressCache.get(id, Address.class);
        if (cachedAddress != null) {
            log.debug("Address found in cache for id: {}", id);
            log.debug("getAddressById for id: {} completed in {} seconds (cache hit)", id, calculateElapsedTime(startTime));
            return Mono.just(cachedAddress);
        }

        String uri = UriComponentsBuilder.fromUriString(path)
                .buildAndExpand(id)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Address.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(maxRetryInterval))
                        .doBeforeRetry((signal) -> {
                            log.debug("Retrying: {}", signal);
                        }))
                .onErrorResume(signal -> Mono.just(Address.defaulAddres()))
                .doOnSuccess(address -> {
                    log.debug("Caching address for id: {}", id);
                    addressCache.put(id, address);
                    log.debug("getAddressById for id: {} completed in {} seconds (cache miss)", id, calculateElapsedTime(startTime));
                });

    }

    private double calculateElapsedTime(Instant startTime) {
        long elapsedTimeMillis = ChronoUnit.MILLIS.between(startTime, Instant.now());
        return (double) elapsedTimeMillis / TimeUnit.SECONDS.toMillis(1);
    }

}
