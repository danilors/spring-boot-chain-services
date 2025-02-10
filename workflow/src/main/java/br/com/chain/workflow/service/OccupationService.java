package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.OccupationsClient;
import br.com.chain.workflow.model.Occupation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationService {

    private static final Logger logger = LoggerFactory.getLogger(OccupationService.class.getName());

    private final OccupationsClient occupationsClient;

    public OccupationService(OccupationsClient occupationsClient) {
        this.occupationsClient = occupationsClient;
    }

    public List<Occupation> getAllOccupations() {
        logger.info("GETTING ALL OCCUPATIONS");
        return occupationsClient.getAllOccupations();
    }
}
