package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.exception.ProfileClientException;
import br.com.chain.workflow_processor.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class ProfileClient {

    private final WebClient webClient;

    @Autowired
    public ProfileClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:33501").build(); // Replace with your address service URL
    }

    public Mono<Profile> getProfiles() {
        return webClient.get()
                .uri("/api/profiles/1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    // Log the error for debugging
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new ProfileClientException("Error: " + response.statusCode() + " - " + errorBody)));
                })
                .bodyToMono(Profile.class)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(500)) // Retry 3 times with 1/2 -second backoff
                        .filter(throwable -> !(throwable instanceof ProfileClientException))
                        .onRetryExhaustedThrow((signal, ex) -> { // Corrected: Use the 'ex' parameter
                            return new ProfileClientException("Failed to get profile after multiple retries", ex.failure());
                        }));
    }
}
