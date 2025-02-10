package br.com.chain.workflow.clients;

import br.com.chain.workflow.clients.fallback.AddressClientFallbackFactory;
import br.com.chain.workflow.config.RetryConfiguration;
import br.com.chain.workflow.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "addressClient", fallbackFactory = AddressClientFallbackFactory.class, configuration = RetryConfiguration.class)
public interface AddressClient {
    @RequestMapping(method = RequestMethod.GET)
    List<Address> getAllAddress();
}
