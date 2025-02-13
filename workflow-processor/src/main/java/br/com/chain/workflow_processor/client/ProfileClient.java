package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .bodyToMono(Profile.class);
    }
}
