package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.model.CommonData;
import br.com.chain.workflow_processor.model.Profile;
import br.com.chain.workflow_processor.model.RequestData;
import br.com.chain.workflow_processor.model.RuleServices;
import br.com.chain.workflow_processor.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;

@Slf4j
@Service
public class ParallelProcessService {

    private final Scheduler virtualThreadScheduler;
    private final ProfileService profileService;
    private final List<CommonService> commonServices;
    private final RuleService ruleService;

    public ParallelProcessService(Scheduler virtualThreadScheduler, ProfileService profileService, List<CommonService> commonServices, RuleService ruleService) {
        this.virtualThreadScheduler = virtualThreadScheduler;
        this.profileService = profileService;
        this.commonServices = commonServices;
        this.ruleService = ruleService;
    }

    public Mono<CommonData> getParallelData(int profileId, int rulesId) {
        // Use Mono.zip to fetch rules and profile concurrently.
        // This can improve readability and slightly improve performance if both calls are independent.
        Mono<RuleServices> rulesMono = ruleService.getRulesById(rulesId);
        Mono<Profile> profileMono = profileService.getProfile(profileId);

        return Mono.zip(rulesMono, profileMono)
                .flatMap(tuple -> {
                    RuleServices ruleServices = tuple.getT1();
                    Profile profileData = tuple.getT2();

                    return Flux.fromIterable(commonServices)
                            .filter(service -> ruleServices.getServices().contains(service.getServiceName()))
                            .filter(CommonService::isActive)
                            .flatMap(service -> service.getData(profileData)
                                    .subscribeOn(virtualThreadScheduler))
                            .collectList()
                            .map(CommonData::fromInformation);
                });
    }
    public Mono<CommonData> getParallelData(RequestData data) {
        return getParallelData(data.profileId(), data.rulesId());
    }
}
