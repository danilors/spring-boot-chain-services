package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.OccupationsClient;
import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Occupation;
import br.com.chain.workflow.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OccupationService implements CompletableService {

    private static final Logger logger = LoggerFactory.getLogger(OccupationService.class.getName());

    private final OccupationsClient occupationsClient;

    public OccupationService(OccupationsClient occupationsClient) {
        this.occupationsClient = occupationsClient;
    }

    public Occupation getOccupationById(int occupationId) {
        logger.info("GETTING ALL OCCUPATIONS");
        return occupationsClient.getOccupationId(occupationId);
    }

    @Override
    public void run(Profile profile, DataWorkflow dataWorkflow) {
        logger.info("running in thread: {}", Thread.currentThread().getName());
        var occupation = this.getOccupationById(profile.occupationId());
        dataWorkflow.setOccupation(occupation);
    }
}
