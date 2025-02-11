package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.OccupationsClient;
import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Occupation;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationService implements CompletableService {

    private static final Logger logger = LoggerFactory.getLogger(OccupationService.class.getName());

    private final OccupationsClient occupationsClient;
    private Disposable dataSubscription;

    public OccupationService(OccupationsClient occupationsClient) {
        this.occupationsClient = occupationsClient;
    }

    @Override
    public void listenTo(DataWorkflow dataWorkflow) {
        dataSubscription = dataWorkflow.observeChanges().subscribe(data -> {
            if (data.getOccupation().isPresent()) {
                stopListening();
            }
            var occupations = getAllOccupations();
            data.setOccupation(occupations);
        });
    }

    public List<Occupation> getAllOccupations() {
        logger.info("GETTING ALL OCCUPATIONS");
        return occupationsClient.getAllOccupations();
    }

    public void stopListening() {
        logger.info("STOP LISTENING OCCUPATIONS");
        if (dataSubscription != null && !dataSubscription.isDisposed()) {
            dataSubscription.dispose();
        }
    }

}
