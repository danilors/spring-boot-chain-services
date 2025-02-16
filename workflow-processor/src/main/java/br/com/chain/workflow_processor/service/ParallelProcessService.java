package br.com.chain.workflow_processor.service;

import static br.com.chain.workflow_processor.ServiceNamesEnum.*;

import br.com.chain.workflow_processor.ServiceNamesEnum;
import br.com.chain.workflow_processor.model.CommonData;
import br.com.chain.workflow_processor.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class ParallelProcessService {


    List<ServiceNamesEnum> activeServices = List.of(ADDRESS, OCCUPATION);

    private final ProfileService profileService;
    private final List<CommonService> commonServices;

    public ParallelProcessService(ProfileService profileService, List<CommonService> commonServices) {
        this.profileService = profileService;
        this.commonServices = commonServices;
    }


    public Mono<CommonData> getParallelData(Integer profileId) {
        return profileService.getProfile(profileId).flatMap(profileData -> {
            return Flux.fromIterable(commonServices)
                    .filter(service -> activeServices.contains(service.getServiceName()))
                    .flatMap(service -> service.getData(profileId))
                    .collectList()
                    .map(CommonData::fromInformation);
        });
    }
}
