package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.client.AddressClient;
import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import br.com.chain.workflow_processor.model.Address;
import br.com.chain.workflow_processor.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class AddressCommonService implements CommonService {


    private final AddressClient AddressClient;

    public AddressCommonService(AddressClient AddressClient) {
        this.AddressClient = AddressClient;
    }

    public Mono<Address> getData(Profile profile) {

        return  getAddressByIdWithTimeout(profile.addressId());
    }
    public Mono<Address> getAddressByIdWithTimeout(Integer id) {
        return AddressClient.getAddressById(id)
                .timeout(Duration.ofSeconds(5)) // Apply a 5-second timeout
                .onErrorResume(throwable -> {
                    // Use the structured logger
                    log.error("Error fetching address for id {}. Returning default. Error: {}", id, throwable.getMessage());
                    return Mono.just(Address.defaulAddres());
                });
    }
    @Override
    public ServiceNamesEnum getServiceName() {
        return ServiceNamesEnum.ADDRESS;
    }

    @Override
    public boolean isActive() {
        return Boolean.TRUE;
    }


}
