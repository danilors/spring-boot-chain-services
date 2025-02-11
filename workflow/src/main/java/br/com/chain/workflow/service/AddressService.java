package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.AddressClient;
import br.com.chain.workflow.model.Address;
import br.com.chain.workflow.model.DataWorkflow;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService implements CompletableService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class.getName());
    private final AddressClient addressClient;

    private Disposable dataSubscription;

    public AddressService(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    @Override
    public void listenTo(DataWorkflow dataWorkflow) {
        dataSubscription = dataWorkflow.observeChanges().subscribe(data -> {
            if (data.getAddress().isPresent()) {
                stopListening();
            }
            var address = getAllAddress();
            data.setAddress(address);
        });

    }

    public List<Address> getAllAddress() {
        logger.info("GETTING ALL ADDRESS");
        return addressClient.getAllAddress();
    }

    public void stopListening() {
        logger.info("STOP LISTENING ADDRESS");
        if (dataSubscription != null && !dataSubscription.isDisposed()) {
            dataSubscription.dispose();
        }
    }

}
