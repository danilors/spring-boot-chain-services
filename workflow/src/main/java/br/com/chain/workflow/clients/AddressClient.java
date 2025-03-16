package br.com.chain.workflow.clients;

import br.com.chain.workflow.model.Address;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "addressClient", url = "http://localhost:33502")
public interface AddressClient {

    @Retryable(retryFor = FeignException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @RequestMapping(value = "/api/address/{addressId}", method = RequestMethod.GET)
    Optional<Address> getAddressById(@PathVariable("addressId") int addressId);
}
