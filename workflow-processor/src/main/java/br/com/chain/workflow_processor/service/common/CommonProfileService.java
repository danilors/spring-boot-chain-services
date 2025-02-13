package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.client.ProfileClient;
import br.com.chain.workflow_processor.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CommonProfileService  implements CommonService {

    private final ProfileClient profileClient;

    public CommonProfileService(ProfileClient profileClient) {
        this.profileClient = profileClient;
    }

    @Override
    public Mono<Profile> getData() {
        return profileClient.getProfiles();
    }


}
