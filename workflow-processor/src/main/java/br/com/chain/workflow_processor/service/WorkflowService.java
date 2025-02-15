package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.model.Address;
import br.com.chain.workflow_processor.model.CentralData;
import br.com.chain.workflow_processor.model.Occupation;
import br.com.chain.workflow_processor.model.Profile;
import br.com.chain.workflow_processor.service.listener.AddressServiceImpl;
import br.com.chain.workflow_processor.service.listener.ListenerService;
import br.com.chain.workflow_processor.service.listener.OccupationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class WorkflowService {

    private final AddressServiceImpl addressService;
    private final OccupationServiceImpl occupationService;
    private final ProfileService profileService;
    private final List<ListenerService> services;


    public WorkflowService(AddressServiceImpl addressService, OccupationServiceImpl occupationService, ProfileService profileService, List<ListenerService> services) {
        this.addressService = addressService;
        this.occupationService = occupationService;
        this.profileService = profileService;
        this.services = services;
    }

    public Mono<Address> getAddress() {
        return addressService.getAddress();
    }

    public Flux<String> getDataStream() {
        return Flux.just("Data 1", "Data 2", "Data 3");
    }

    public Mono<Profile> getProfile() {
        return profileService.getProfile();
    }

    public Mono<Occupation> getOccupation() {
        return occupationService.getOccupation();
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

    private void registerCommonServices(CentralData centralData) {
        services.forEach(service -> service.listenToCentralDataChanges(centralData));
    }
}
