package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.model.Occupation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
public class OccupationClient extends  AbstractClient {

    @Autowired
    public OccupationClient(WebClient.Builder webClientBuilder,
                            @Value("${occupations.api.baseUrl}") String baseUrl,
                            @Value("${occupations.api.path}") String path,
                            @Value("${occupations.api.maxRetry}") Integer maxRetry,
                            @Value("${occupations.api.maxRetryInterval}") Integer maxRetryInterval) {
        this.path = path;
        this.maxRetry = maxRetry;
        this.maxRetryInterval = maxRetryInterval;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Occupation> getOccupationById(Integer id) {
        String uri = UriComponentsBuilder.fromUriString(path)
                .buildAndExpand(id)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Occupation.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(maxRetryInterval))
                        .doBeforeRetry((signal) -> {
                            log.debug("Retrying: {}", signal);
                        }))
                .onErrorResume(ex -> Mono.just(Occupation.defaultOccupation()));

    }
}