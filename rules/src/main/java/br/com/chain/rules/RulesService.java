package br.com.chain.rules;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RulesService {

    private final RulesRepository rulesRepository;

    public RulesService(RulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    public List<Rules> getAllRules() {
        return rulesRepository.findAll();
    }

    public Rules getRulesById(Long id) {
        return rulesRepository.findById(id).orElse(null);
    }

    public Rules createRules(Rules rules) {
        return rulesRepository.save(rules);
    }

    public Rules updateRules(Long id, Rules rules) {
        Rules existingRules = rulesRepository.findById(id).orElse(null);

        if (existingRules != null) {
            existingRules.setCreated(LocalDate.now());
            existingRules.setDescription(rules.getDescription());
            return rulesRepository.save(existingRules);
        }
        return null;
    }

    public void deleteRules(Long id) {
        rulesRepository.deleteById(id);
    }
}