package br.com.chain.workflow.clients;

import br.com.chain.workflow.model.Occupation;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "occupationsClient", url = "http://localhost:33503")
public interface OccupationsClient {

    @Retryable(retryFor = FeignException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @RequestMapping(value = "/api/occupations/{occupationId}", method = RequestMethod.GET)
    Optional<Occupation> getOccupationId(@PathVariable("occupationId") int occupationId);
}
