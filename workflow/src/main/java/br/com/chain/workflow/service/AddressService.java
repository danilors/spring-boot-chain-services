package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.AddressClient;
import br.com.chain.workflow.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class.getName());

    @Autowired
    private final AddressClient addressClient;

    public AddressService(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    public List<Address> getAllAddress() {
        logger.info("GETTING ALL ADDRESS");
        return addressClient.getAllAddress();
    }
}
