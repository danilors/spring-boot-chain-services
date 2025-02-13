package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.model.Address;
import br.com.chain.workflow_processor.model.Occupation;
import br.com.chain.workflow_processor.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WorkflowService {

    private final AddressService addressService;
    private final OccupationService occupationService;
    private final ProfileService profileService;

    public WorkflowService(AddressService addressService, OccupationService occupationService, ProfileService profileService) {
        this.addressService = addressService;
        this.occupationService = occupationService;
        this.profileService = profileService;
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
}
