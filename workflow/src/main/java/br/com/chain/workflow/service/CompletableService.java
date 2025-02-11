package br.com.chain.workflow.service;

import br.com.chain.workflow.model.DataWorkflow;

public interface CompletableService {
    void listenTo(DataWorkflow dataWorkflow);
}
