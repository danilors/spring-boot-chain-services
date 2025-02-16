package br.com.chain.workflow_processor.controller;

import br.com.chain.workflow_processor.model.*;
import br.com.chain.workflow_processor.service.ParallelProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {


    private final ParallelProcessService parallelProcessService;

    public WorkFlowController(ParallelProcessService parallelProcessService) {
        this.parallelProcessService = parallelProcessService;
    }

    @GetMapping("/parallel/{profileId}/{ruleId}")
    public Mono<CommonData> getAll(@PathVariable String profileId, @PathVariable String ruleId) {
        return parallelProcessService.getParallelData(Integer.parseInt(profileId), Integer.parseInt(ruleId));
    }

    @PostMapping("/parallel")
    public Mono<CommonData> getAll(@RequestBody RequestData data){
        return parallelProcessService.getParallelData(data);
    }
}
