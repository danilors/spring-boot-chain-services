package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import br.com.chain.workflow_processor.client.OccupationClient;
import br.com.chain.workflow_processor.model.Occupation;
import br.com.chain.workflow_processor.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OccupationCommonService implements CommonService {


    @Override
    public ServiceNamesEnum getServiceName() {
        return ServiceNamesEnum.OCCUPATION;
    }

    private final OccupationClient occupationClient;

    public OccupationCommonService(OccupationClient occupationClient) {
        this.occupationClient = occupationClient;
    }

    @Override
    public Mono<Occupation> getData(Profile profile) {
        log.info("getting occupation data");
        log.info("running in thread: {}", Thread.currentThread().getName());
        return occupationClient.getOccupationById(profile.occupationId());
    }

    @Override
    public boolean isActive() {
        return Boolean.TRUE;
    }

}
