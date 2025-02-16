package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.client.ProfileClient;
import br.com.chain.workflow_processor.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProfileService {

    private final ProfileClient profileClient;

    public ProfileService(ProfileClient profileClient) {
        this.profileClient = profileClient;
    }

    Mono<Profile> getProfile(Integer profileId) {
        return profileClient.getProfileById(profileId);
    }

}
