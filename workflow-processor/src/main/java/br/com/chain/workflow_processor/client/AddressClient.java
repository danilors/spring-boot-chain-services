package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AddressClient {

    private  final WebClient webClient;

    @Autowired
    public AddressClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:33502").build(); // Replace with your address service URL
    }

        public Mono<Address> getAddressData() {
        return webClient.get()
                .uri("/api/address/1") // Replace with the correct endpoint
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Address.class);
    }
}
