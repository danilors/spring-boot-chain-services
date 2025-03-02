package br.com.chain.workflow.controller;


import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.service.WorkFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    private static final Logger logger = LoggerFactory.getLogger(WorkFlowController.class);

    private final WorkFlowService workFlowService;


    public WorkFlowController(WorkFlowService workFlowService) {
        this.workFlowService = workFlowService;
    }

    @GetMapping(value = "/start/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataWorkflow> startFlow(@PathVariable("profileId") int profileId) { // Correct return type: Single<DataWorkflow>
        logger.info("STARTING WORKFLOW");
        return ResponseEntity.ok(workFlowService.start(profileId));

    }
}
