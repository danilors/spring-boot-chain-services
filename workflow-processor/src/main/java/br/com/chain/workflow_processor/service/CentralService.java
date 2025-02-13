package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.model.*;
import br.com.chain.workflow_processor.service.common.CommonService;
import br.com.chain.workflow_processor.service.listener.ListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class CentralService {

    private final List<ListenerService> services;

    private final ProfileService profileService;

    private final List<CommonService> commonServices;

    public CentralService(List<ListenerService> services, ProfileService profileService, List<CommonService> commonServices) {
        this.services = services;
        this.profileService = profileService;
        this.commonServices = commonServices;
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

    public Mono<CommonData> getParallelData() {
        Mono<Profile> profile  = profileService.getProfile();
        return Flux.fromIterable(commonServices)
                .flatMap(CommonService::getData)
                .collectList()
                .map(CommonData::fromInformations);
    }
}
