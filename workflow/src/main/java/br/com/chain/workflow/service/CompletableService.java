package br.com.chain.workflow.service;

import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Profile;

public interface CompletableService {
    void run(Profile profile, DataWorkflow dataWorkflow);
}
