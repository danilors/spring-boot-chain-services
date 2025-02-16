package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.ServiceNamesEnum;
import br.com.chain.workflow_processor.client.AddressClient;
import br.com.chain.workflow_processor.model.Address;
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

    public Mono<Address> getData(Integer id) {
        return addressClient.getAddressById(id);
    }

    @Override
    public ServiceNamesEnum getServiceName() {
        return ServiceNamesEnum.ADDRESS;
    }


}
