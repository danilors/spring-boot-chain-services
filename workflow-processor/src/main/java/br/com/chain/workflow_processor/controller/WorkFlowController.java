package br.com.chain.workflow_processor.controller;

import br.com.chain.workflow_processor.model.*;
import br.com.chain.workflow_processor.service.ParallelProcessService;
import br.com.chain.workflow_processor.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    private final WorkflowService workflowService;
    private final ParallelProcessService parallelProcessService;

    public WorkFlowController(WorkflowService workflowService, ParallelProcessService parallelProcessService) {
        this.workflowService = workflowService;
        this.parallelProcessService = parallelProcessService;
    }

    @GetMapping("/stream")
    public Flux<String> getStreamData() {
        return workflowService.getDataStream();
    }

    @GetMapping("/address")
    public Mono<Address> getAddress() {
        return workflowService.getAddress();
    }

    @GetMapping("/profile")
    public Mono<Profile> getProfile() {
        return workflowService.getProfile();
    }

    @GetMapping("/occupation")
    public Mono<Occupation> getOccupation() {
        return workflowService.getOccupation();
    }

    @GetMapping("/listeners")
    public Mono<CentralData> start() {
        return workflowService.start();
    }

    @GetMapping("/parallel")
    public Mono<CommonData> getAll() {
        return parallelProcessService.getParallelData();
    }

}
