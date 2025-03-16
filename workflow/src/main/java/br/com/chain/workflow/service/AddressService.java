package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.AddressClient;
import br.com.chain.workflow.model.Address;
import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements CompletableService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class.getName());
    private final AddressClient addressClient;


    public AddressService(AddressClient addressClient) {
        this.addressClient = addressClient;
    }


    public Address getAllAddress(int addressId) {
        logger.info("GETTING ALL ADDRESS");
        return addressClient.getAddressById(addressId).orElse(Address.defaultAddress());
    }

    @Override
    public void run(Profile profile, DataWorkflow dataWorkflow) {
        logger.info("running in thread: {}", Thread.currentThread().getName());
        var address = this.getAllAddress(profile.addressId());
        dataWorkflow.setAddress(address);
    }
}
