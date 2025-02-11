package br.com.chain.workflow.clients.fallback;


import br.com.chain.workflow.clients.OccupationsClient;
import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.model.Occupation;
import br.com.chain.workflow.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OccupationClientFallbackFactory implements FallbackFactory<OccupationsClient> {

    private static final Logger logger = LoggerFactory.getLogger(OccupationClientFallbackFactory.class.getName());

    @Override
    public OccupationsClient create(Throwable cause) {
        logger.error(cause.getMessage());
        return new OccupationsClient() {
            @Override
            public List<Occupation> getAllOccupations() {
                logger.info("response -> default Profile value from callback");
                return List.of(new Occupation(-1L, "default", 0));
            }
        };
    }
}

