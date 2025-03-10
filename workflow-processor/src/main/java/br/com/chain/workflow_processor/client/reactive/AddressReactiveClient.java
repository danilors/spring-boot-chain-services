package br.com.chain.workflow_processor.client.reactive;

import br.com.chain.workflow_processor.model.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "external-service", url = "${address.api.baseUrl}")
public interface AddressReactiveClient {

    @GetMapping("${address.api.path}")
    public Mono<Address> getAddressById(@PathVariable("id") Integer id);
}
