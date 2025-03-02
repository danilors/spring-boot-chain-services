package br.com.chain.workflow.service;

import br.com.chain.workflow.model.DataWorkflow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowService {

    private final List<CompletableService> services;
    private final ProfileService profileService;
    private final TaskExecutorService taskExecutorService;


    public WorkFlowService(List<CompletableService> services, ProfileService profileService, TaskExecutorService taskExecutorService) {
        this.services = services;
        this.profileService = profileService;
        this.taskExecutorService = taskExecutorService;
    }
    public DataWorkflow start(int profileId) {
        var profile = profileService.getProfileById(profileId);
        return taskExecutorService.processTask(profile, services);
    }
}
