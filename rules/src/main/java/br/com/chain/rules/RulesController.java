package br.com.chain.rules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rules")
public class RulesController {

    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @GetMapping
    public List<Rules> getAllRules() {
        return rulesService.getAllRules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRulesById(@PathVariable Long id) {
        var result = rulesService.getRulesById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createRules(@RequestBody Rules rules) {
        return ResponseEntity.ok(rulesService.createRules(rules));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRules(@PathVariable Long id, @RequestBody Rules rules) {
        var result = rulesService.updateRules(id, rules);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRules(@PathVariable Long id) {
        rulesService.deleteRules(id);
        return ResponseEntity.ok().build();
    }
}