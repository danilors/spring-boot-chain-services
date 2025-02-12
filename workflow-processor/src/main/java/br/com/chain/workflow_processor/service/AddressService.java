package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.client.AddressClient;
import br.com.chain.workflow_processor.model.Address;
import br.com.chain.workflow_processor.model.CentralData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AddressService extends CustomDisposable implements CommonService {

    private final AddressClient addressClient;

    public AddressService(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    public Mono<Address> getAddress() {
        return addressClient.getAddressData();
    }

    @Override
    public void listenToCentralDataChanges(CentralData centralData) {
        log.info("register listener changes for Address Service");
        centralData.listenerChange().log().subscribe(cd -> {
            if (cd.hasNotAddress()) {
                disposableService = getAddress().log().subscribe(cd::setAddress);
            } else {
                log.info("calling dispose {}", AddressService.class.getSimpleName());
                dispose();
            }
        });
    }
}
