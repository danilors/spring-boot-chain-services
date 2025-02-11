package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.model.CentralData;

public interface CommonService {
    void listenToCentralDataChanges(CentralData centralData);
}
