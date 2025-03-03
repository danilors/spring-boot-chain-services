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
    public DataWorkflow startTaskExecutor(int profileId) {
        var profile = profileService.getProfileById(profileId);
        return taskExecutorService.processTask(profile, services);
    }

    public DataWorkflow startParallelStream(int profileId) {
        var profile = profileService.getProfileById(profileId);
        var data = new DataWorkflow(profile);
        services.parallelStream().forEach(service -> service.run(profile, data));
        return data;
    }

}
