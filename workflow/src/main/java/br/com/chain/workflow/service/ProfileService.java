package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Profile;
import br.com.chain.workflow.model.ProfileWrapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class.getName());

    private final ProfileClient profileClient;


    public ProfileService(ProfileClient profileClient) {
        this.profileClient = profileClient;

    }

    public Single<ProfileWrapper> getAllProfiles() {
        logger.info("GETTING ALL PROFILES");
        return Single.fromCallable(() -> new ProfileWrapper(profileClient.getAllProfiles()))
                .subscribeOn(Schedulers.io());
    }
}
