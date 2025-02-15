package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.exception.ProfileClientException;
import br.com.chain.workflow_processor.model.Profile;
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
public class ProfileClient {

    private final WebClient webClient;
    private final String path;
    private final Integer maxRetry;
    private final Integer maxRetryInterval;

    @Autowired
    public ProfileClient(WebClient.Builder webClientBuilder,
                         @Value("${profiles.api.baseUrl}") String baseUrl,
                         @Value("${profiles.api.path}") String path,
                         @Value("${profiles.api.maxRetry}") Integer maxRetry,
                         @Value("${profiles.api.maxRetryInterval}") Integer maxRetryInterval


    ) {
        this.path = path;
        this.maxRetry = maxRetry;
        this.maxRetryInterval = maxRetryInterval;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Profile> getProfileById(Integer id) {
        String uri = UriComponentsBuilder.fromUriString(path)
                .buildAndExpand(id)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new ProfileClientException("Error: " + response.statusCode() + " - " + errorBody)));
                })
                .bodyToMono(Profile.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(maxRetryInterval))
                        .filter(throwable -> !(throwable instanceof ProfileClientException))
                        .onRetryExhaustedThrow((signal, ex) -> {
                            return new ProfileClientException("Failed to get profile after multiple retries", ex.failure());
                        }));
    }
}
