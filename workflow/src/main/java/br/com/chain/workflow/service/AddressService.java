package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.AddressClient;
import br.com.chain.workflow.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private final AddressClient addressClient;

    public AddressService(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    public List<Address> getAllAddress() {
        return addressClient.getAllAddress();
    }
}
