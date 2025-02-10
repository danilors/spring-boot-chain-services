package br.com.chain.workflow.clients.fallback;

import br.com.chain.workflow.clients.AddressClient;
import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.model.Address;
import br.com.chain.workflow.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressClientFallbackFactory implements FallbackFactory<AddressClient> {

    private static final Logger logger = LoggerFactory.getLogger(AddressClientFallbackFactory.class.getName());

    @Override
    public AddressClient create(Throwable cause) {
        logger.error(cause.getMessage());
        return new AddressClient() {
            @Override
            public List<Address> getAllAddress() {
                logger.info("response -> default Address value from callback");
                return List.of(new Address(-1L, "default", 0));
            }
        };
    }
}




