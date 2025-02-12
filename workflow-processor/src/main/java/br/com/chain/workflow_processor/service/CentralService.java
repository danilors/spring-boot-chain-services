package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.model.CentralData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class CentralService {

    private final List<CommonService> services;

    private final ProfileService profileService;

    public CentralService(List<CommonService> services, ProfileService profileService) {
        this.services = services;
        this.profileService = profileService;

    }

    private void registerCommonServices(CentralData centralData) {
        services.forEach(service -> service.listenToCentralDataChanges(centralData));
    }

    public Mono<CentralData> start() {
        CentralData centralData = new CentralData();

        return Mono.defer(() -> {
                    registerCommonServices(centralData);
                    return Mono.empty();
                }).then(profileService.getProfile())
                .onErrorResume(error -> {
                    log.error("Error trying to get profile info: ", error);
                    return Mono.empty();
                })
                .flatMap(profileData -> {
                    centralData.setProfile(profileData);
                    return centralData.listenerChange()
                            .filter(CentralData::isCompleted)
                            .next();
                });
    }

}
