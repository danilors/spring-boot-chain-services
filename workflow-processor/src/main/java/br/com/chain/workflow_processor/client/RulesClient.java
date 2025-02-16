package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import br.com.chain.workflow_processor.exception.RulesClientException;
import br.com.chain.workflow_processor.model.RuleServices;
import br.com.chain.workflow_processor.model.Rules;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Component
public class RulesClient extends AbstractClient {
    public RulesClient(WebClient.Builder webClientBuilder,
                       @Value("${rules.api.baseUrl}") String baseUrl,
                       @Value("${rules.api.path}") String path,
                       @Value("${rules.api.maxRetry}") Integer maxRetry,
                       @Value("${rules.api.maxRetryInterval}") Integer maxRetryInterval
    ) {
        this.path = path;
        this.maxRetry = maxRetry;
        this.maxRetryInterval = maxRetryInterval;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<RuleServices> getRulesById(Integer id) {
        String uri = UriComponentsBuilder.fromUriString(path)
                .buildAndExpand(id)
                .toUriString();

        return webClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RulesClientException("Error: " + response.statusCode() + " - " + errorBody))))
                .bodyToMono(Rules.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(maxRetryInterval))
                        .filter(throwable -> !(throwable instanceof RulesClientException))
                        .onRetryExhaustedThrow((signal, ex) -> new RulesClientException("Failed to get rules after multiple retries", ex.failure()))
                        .doBeforeRetry((signal) -> {
                            log.debug("Retrying: {}", signal);
                        }))
                .flatMap(response -> Mono.just(fromRules(response)));


    }

    private static RuleServices fromRules(Rules response) {
        return new RuleServices(response.getRules().stream()
                .map(rule -> ServiceNamesEnum.fromString(rule.getDescription()))
                .filter(Objects::nonNull)
                .distinct()
                .toList());
    }

}
