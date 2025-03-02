package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.client.RulesClient;
import br.com.chain.workflow_processor.model.RuleServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RuleService {
    private final RulesClient rulesClient;

    public RuleService(RulesClient rulesClient) {
        this.rulesClient = rulesClient;
    }


    public Mono<RuleServices> getRulesById(int id) {
        return rulesClient.getRulesById(id);
    }
}
