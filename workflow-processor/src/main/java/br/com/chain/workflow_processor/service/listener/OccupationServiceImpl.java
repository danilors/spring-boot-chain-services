package br.com.chain.workflow_processor.service.listener;

import br.com.chain.workflow_processor.client.OccupationClient;
import br.com.chain.workflow_processor.model.CentralData;
import br.com.chain.workflow_processor.model.Occupation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OccupationServiceImpl extends AbstractCustomDisposable implements ListenerService {

    private final OccupationClient occupationClient;


    public OccupationServiceImpl(OccupationClient occupationClient) {
        this.occupationClient = occupationClient;
    }

    public Mono<Occupation> getOccupation(Integer id) {
        return occupationClient.getOccupationById(id);
    }

    @Override
    public void listenToCentralDataChanges(CentralData centralData) {
        log.info("register listener changes for Occpation Service");
        centralData.listenerChange().log().subscribe(cd -> {
            if (cd.hasNotOccupation()) {
                disposableService = getOccupation(centralData.getProfile().id()).subscribe(cd::setOccupation);
            } else {
                log.info("calling dispose {}", OccupationServiceImpl.class.getSimpleName());
                this.dispose();
            }
        });
    }
}
