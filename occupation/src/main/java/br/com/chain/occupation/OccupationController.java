package br.com.chain.occupation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/occupations")
public class OccupationController {

    private final OccupationService occupationService;

    public OccupationController(OccupationService occupationService) {
        this.occupationService = occupationService;
    }

    @GetMapping
    public List<Occupation> getAllOccupations() {
        return occupationService.getAllOccupations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOccupationById(@PathVariable Long id) {
        var result = occupationService.getOccupationById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createOccupation(@RequestBody Occupation occupation) {
        return ResponseEntity.ok(occupationService.createOccupation(occupation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOccupation(@PathVariable Long id, @RequestBody Occupation occupation) {

        var result = occupationService.updateOccupation(id, occupation);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOccupation(@PathVariable Long id) {
        occupationService.deleteOccupation(id);
        return ResponseEntity.ok().build();
    }
}