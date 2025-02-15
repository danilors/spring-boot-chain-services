package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.exception.AddressClientException;
import br.com.chain.workflow_processor.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class AddressClient {

    private final WebClient webClient;
    private final String path;
    private final Integer maxRetry;
    private final Integer maxRetryInterval;

    @Autowired
    public AddressClient(WebClient.Builder webClientBuilder,
                         @Value("${address.api.baseUrl}") String baseUrl,
                         @Value("${address.api.path}") String path,
                         @Value("${address.api.maxRetry}") Integer maxRetry,
                         @Value("${address.api.maxRetryInterval}") Integer maxRetryInterval) {
        this.path = path;
        this.maxRetry = maxRetry;
        this.maxRetryInterval = maxRetryInterval;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Address> getAddressById(Integer id) {
        String uri = UriComponentsBuilder.fromUriString(path)
                .buildAndExpand(id)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new AddressClientException("Error: " + response.statusCode() + " - " + errorBody)));
                })
                .bodyToMono(Address.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(maxRetryInterval))
                        .filter(throwable -> !(throwable instanceof AddressClientException))
                        .onRetryExhaustedThrow((signal, ex) -> {
                            return new AddressClientException("Failed to get address after multiple retries", ex.failure());
                        }));

    }
}
