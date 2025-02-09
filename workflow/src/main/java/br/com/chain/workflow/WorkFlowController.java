package br.com.chain.workflow;


import br.com.chain.workflow.clients.ProfileClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {

    private final ProfileService profileService;

    public WorkFlowController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/start")
    public ResponseEntity<?> startFlow() {
        var profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }
}
