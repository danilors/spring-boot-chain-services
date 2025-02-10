package br.com.chain.workflow.controller;


import br.com.chain.workflow.model.WorkFlow;
import br.com.chain.workflow.service.AddressService;
import br.com.chain.workflow.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    private final ProfileService profileService;
    private final AddressService addressService;

    public WorkFlowController(ProfileService profileService, AddressService addressService) {
        this.profileService = profileService;
        this.addressService = addressService;
    }

    @GetMapping("/start")
    public ResponseEntity<?> startFlow() {
        var profiles = profileService.getAllProfiles();
        var address = addressService.getAllAddress();

        return ResponseEntity.ok(new WorkFlow(profiles, address));
    }
}
