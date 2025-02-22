package br.com.chain.workflow_processor.service.common;

import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import br.com.chain.workflow_processor.model.Profile;
import reactor.core.publisher.Mono;

public interface CommonService {
    Mono<?> getData(Profile profile);

    ServiceNamesEnum getServiceName();

    boolean isActive();
}
