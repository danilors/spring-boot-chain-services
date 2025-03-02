package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import br.com.chain.workflow_processor.client.AddressClient;
import br.com.chain.workflow_processor.model.Address;
import br.com.chain.workflow_processor.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressCommonService implements CommonService {

    private final AddressClient addressClient;

    public AddressCommonService(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    public Mono<Address> getData(Profile profile) {
        return addressClient.getAddressById(profile.addressId());
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
