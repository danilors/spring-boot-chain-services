package br.com.chain.workflow.clients;

import br.com.chain.workflow.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "addressClient", url = "http://localhost:33502")
public interface AddressClient {
    @RequestMapping(value = "/api/address/{addressId}", method = RequestMethod.GET)
    Address getAddressById(@PathVariable("addressId") int addressId);
}
