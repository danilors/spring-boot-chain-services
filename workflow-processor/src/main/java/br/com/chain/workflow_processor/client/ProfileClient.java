package br.com.chain.workflow_processor.client;

import br.com.chain.workflow_processor.model.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProfileClient {

    public Mono<Profile> getProfileData() {
        return Mono.just(new Profile("DANILO"));
    }
}
