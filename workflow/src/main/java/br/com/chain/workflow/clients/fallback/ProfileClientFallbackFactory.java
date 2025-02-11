package br.com.chain.workflow.clients.fallback;

import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileClientFallbackFactory implements FallbackFactory<ProfileClient> {

    private static final Logger logger = LoggerFactory.getLogger(ProfileClientFallbackFactory.class.getName());

    @Override
    public ProfileClient create(Throwable cause) {
        logger.error(cause.getMessage());
        return new ProfileClient() {
            @Override
            public List<Profile> getAllProfiles() {
                logger.info("response -> default Profile value from callback");
                return List.of(new Profile(-1L, "default", "default"));
            }
        };
    }
}




