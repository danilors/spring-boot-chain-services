package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.exception.OccupationClientException;
import br.com.chain.workflow_processor.model.Occupation;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
@Component
public class OccupationAWSClient {

    private final AWSLambda awsLambda;
    private final String functionName;
    private final Integer maxRetry;
    private final Integer maxRetryInterval;
    private final ObjectMapper objectMapper; // For JSON


    public OccupationAWSClient(AWSLambda awsLambda,
                            @Value("${occupations.lambda.functionName}") String functionName,
                            @Value("${occupations.maxRetry}") Integer maxRetry,
                            @Value("${occupations.maxRetryInterval}") Integer maxRetryInterval,
                            ObjectMapper objectMapper) { // Inject ObjectMapper
        this.awsLambda = awsLambda;
        this.functionName = functionName;
        this.maxRetry = maxRetry;
        this.maxRetryInterval = maxRetryInterval;
        this.objectMapper = objectMapper;
    }

    public Mono<Occupation> getOccupationById(Integer id) {
            String payload = parsePayloadToJSON(id); // Serialize ID to JSON

            InvokeRequest request = new InvokeRequest()
                    .withFunctionName(functionName)
                    .withPayload(payload);

            return Mono.fromCallable(() -> awsLambda.invoke(request)) // Invoke Lambda asynchronously
                    .subscribeOn(Schedulers.boundedElastic()) // Use a bounded elastic scheduler
                    .map(InvokeResult::getPayload)
                    .map(payloadBytes -> new String(payloadBytes.array(), StandardCharsets.UTF_8)) // Convert bytes to String
                    .flatMap(json -> {
                        try {
                            return Mono.just(objectMapper.readValue(json, Occupation.class)); // Deserialize JSON to Occupation
                        } catch (Exception e) {
                            log.error("Error deserializing Lambda response: {}", e.getMessage(), e);
                            return Mono.error(new OccupationClientException("Error deserializing Lambda response", e));
                        }
                    })
                    .retryWhen(Retry.backoff(maxRetry, Duration.ofMillis(maxRetryInterval))
                            .doBeforeRetry((signal) -> log.debug("Retrying Lambda invocation: {}", signal)))
                    .onErrorResume(ex -> Mono.just(Occupation.defaultOccupation())); // Fallback

    }

    private String parsePayloadToJSON(Integer id) {
        try {
            return objectMapper.writeValueAsString(id);
        } catch (JsonProcessingException e) {
            throw new OccupationClientException(e.getMessage(), e);
        }
    }


}