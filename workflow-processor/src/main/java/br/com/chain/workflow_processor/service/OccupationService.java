package br.com.chain.workflow_processor.service;

import br.com.chain.workflow_processor.client.OccupationClient;
import br.com.chain.workflow_processor.model.CentralData;
import br.com.chain.workflow_processor.model.Occupation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OccupationService implements CommonService {

    private final OccupationClient occupationClient;

    public OccupationService(OccupationClient occupationClient) {
        this.occupationClient = occupationClient;
    }

    public Mono<Occupation> getOccupation() {
        return occupationClient.getOccupationData();
    }

    @Override
    public void listenToCentralDataChanges(CentralData centralData) {
        centralData.listenerChange().subscribe(cd -> {
            if (cd.getProfile() != null && cd.getOccupation() == null) {
                getOccupation()
                        .subscribe(cd::setOccupation);
            }
        });
    }
}
