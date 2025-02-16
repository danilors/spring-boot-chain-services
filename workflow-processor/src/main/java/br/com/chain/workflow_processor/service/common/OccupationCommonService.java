package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.ServiceNamesEnum;
import br.com.chain.workflow_processor.client.OccupationClient;
import br.com.chain.workflow_processor.model.Occupation;
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
    public Mono<Occupation> getData(Integer profileId) {
        return occupationClient.getOccupationById(profileId);
    }


}
