package br.com.chain.workflow.service;

import br.com.chain.workflow.model.DataWorkflow;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowService {


    private final DataWorkflow dataWorkflow;
    private final ProfileService profileService;
    private final List<CompletableService> services;

    public WorkFlowService(DataWorkflow dataWorkflow, ProfileService profileService, List<CompletableService> services) {
        this.dataWorkflow = dataWorkflow;
        this.profileService = profileService;
        this.services = services;
    }

    public Single<DataWorkflow> start() {
        // 1. Subscribe to profileService and set profiles in dataWorkflow
        return profileService.getAllProfiles().;// 3. Return the dataWorkflow when all is complete
    }

}
