package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.client.reactive.AddressReactiveClient;
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


    private final AddressReactiveClient addressReactiveClient;

    public AddressCommonService(AddressReactiveClient addressReactiveClient) {
        this.addressReactiveClient = addressReactiveClient;
    }

    public Mono<Address> getData(Profile profile) {
        log.info("getting address data");
        log.info("running in thread: {}", Thread.currentThread().getName());
        return  getAddressByIdWithTimeout(profile.addressId());
    }
    public Mono<Address> getAddressByIdWithTimeout(Integer id) {
        return addressReactiveClient.getAddressById(id)
                .timeout(Duration.ofSeconds(5)) // Apply a 5-second timeout
                .onErrorResume(throwable -> {
                    // Log the error and return a default Address
                    System.err.println("Error occurred: " + throwable.getMessage());
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
