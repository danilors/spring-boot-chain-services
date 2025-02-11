package br.com.chain.workflow_processor.controller;

import br.com.chain.workflow_processor.model.WorkFlowData;
import br.com.chain.workflow_processor.service.WorkflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    private final WorkflowService workflowService;

    public WorkFlowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping("/data")
    public Flux<WorkFlowData> getData() {
        return workflowService.processData();
    }

}
