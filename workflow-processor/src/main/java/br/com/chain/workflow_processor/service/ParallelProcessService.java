package br.com.chain.workflow_processor.service;

import static br.com.chain.workflow_processor.enums.ServiceNamesEnum.*;

import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import br.com.chain.workflow_processor.model.CommonData;
import br.com.chain.workflow_processor.model.RequestData;
import br.com.chain.workflow_processor.model.RuleServices;
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
    private final RuleService ruleService;

    public ParallelProcessService(ProfileService profileService, List<CommonService> commonServices, RuleService ruleService) {
        this.profileService = profileService;
        this.commonServices = commonServices;
        this.ruleService = ruleService;
    }

    public Mono<CommonData> getParallelData(int profileId, int rulesId) {
        return ruleService.getRulesById(rulesId)
                .flatMap(ruleServices -> profileService.getProfile(profileId)
                        .flatMap(profileData -> Flux.fromIterable(commonServices)
                                .filter(service -> ruleServices.getServices().contains(service.getServiceName()))
                                .filter(CommonService::isActive)
                                .flatMap(service -> service.getData(profileData))
                                .collectList()
                                .map(CommonData::fromInformation)));
    }

    public Mono<CommonData> getParallelData(RequestData data) {
        return getParallelData(data.profileId(), data.rulesId());
    }
}
