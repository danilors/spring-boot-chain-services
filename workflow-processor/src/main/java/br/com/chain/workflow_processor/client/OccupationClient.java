package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.model.Occupation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OccupationClient {

    private final WebClient webClient;

    @Autowired
    public OccupationClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:33503").build(); // Replace with your address service URL
    }

    public Mono<Occupation> getOccupationData() {
        return webClient.get()
                .uri("/api/occupations/1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Occupation.class);
    }
}
