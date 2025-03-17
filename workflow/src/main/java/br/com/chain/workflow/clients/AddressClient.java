package br.com.chain.workflow.clients;

import br.com.chain.workflow.model.Address;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "addressClient", url = "${api.address.url}")
public interface AddressClient {
    Logger logger = LoggerFactory.getLogger(AddressClient.class);

    @Retryable(retryFor = FeignException.class, maxAttempts = 3, backoff = @Backoff(delay = 200))
    @RequestMapping(value = "/api/address/{addressId}", method = RequestMethod.GET)
    Optional<Address> getAddressById(@PathVariable("addressId") int addressId);
 
}
