package br.com.chain.workflow_processor.service.listener;

import br.com.chain.workflow_processor.model.CentralData;

public interface ListenerService {
    void listenToCentralDataChanges(CentralData centralData);
}
