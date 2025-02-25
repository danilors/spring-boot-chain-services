package br.com.chain.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;


    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable int id) {
        log.info("[START] getting profile by id {}", id);
      
        var result = profileService.getProfileById(id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("[END] getting profile result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok(profileService.createProfile(profile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable int id, @RequestBody Profile profile) {

        var result = profileService.updateProfile(id, profile);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable int id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok().build();
    }
}