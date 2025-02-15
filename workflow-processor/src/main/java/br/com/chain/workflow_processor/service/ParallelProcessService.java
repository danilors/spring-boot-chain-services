package br.com.chain.workflow_processor.service;

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

    private final ProfileService profileService;
    private final List<CommonService> commonServices;

    public ParallelProcessService(ProfileService profileService, List<CommonService> commonServices) {
        this.profileService = profileService;
        this.commonServices = commonServices;
    }


    public Mono<CommonData> getParallelData() {
        return profileService.getProfile().flatMap(profileData -> {
            return Flux.fromIterable(commonServices)
                    .flatMap(CommonService::getData)
                    .collectList()
                    .map(CommonData::fromInformation);
        });
    }
}
