package br.com.chain.workflow.controller;


import br.com.chain.workflow.model.WorkFlow;
import br.com.chain.workflow.service.AddressService;
import br.com.chain.workflow.service.OccupationService;
import br.com.chain.workflow.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    private static final Logger logger = LoggerFactory.getLogger(WorkFlowController.class);

    private final ProfileService profileService;
    private final AddressService addressService;
    private final OccupationService occupationService;

    public WorkFlowController(ProfileService profileService, AddressService addressService, OccupationService occupationService) {
        this.profileService = profileService;
        this.addressService = addressService;
        this.occupationService = occupationService;
    }

    @GetMapping("/start")
    public ResponseEntity<?> startFlow() {
        logger.info("STARTING WORKFLOW");
        var profiles = profileService.getAllProfiles();
        var address = addressService.getAllAddress();
        var occupations = occupationService.getAllOccupations();
        logger.info("END OF WORKFLOW");
        return ResponseEntity.ok(new WorkFlow(profiles, address, occupations));
    }
}
