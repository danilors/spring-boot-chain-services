package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.ServiceNamesEnum;
import reactor.core.publisher.Mono;

public interface CommonService {
    Mono<?> getData(Integer profileId);

    ServiceNamesEnum getServiceName();
}
