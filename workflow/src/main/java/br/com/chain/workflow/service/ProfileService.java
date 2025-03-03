package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.model.Profile;
import br.com.chain.workflow.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class.getName());

    private final ProfileClient profileClient;
    private final ProfileRepository profileRepository;


    public ProfileService(ProfileClient profileClient, ProfileRepository profileRepository) {
        this.profileClient = profileClient;

        this.profileRepository = profileRepository;
    }

    public Profile getProfileById(int profileId) {
        logger.info("getting profile {}", profileId);
        var found = profileRepository.findById(String.valueOf(profileId));
        if (found.isPresent()) {
            logger.info("profile with id: {} found on redis cache", profileId);
            return found.get();
        }
        logger.info("profile with id: {} not found on Redis", profileId);
        var profile = profileClient.getProfileById(profileId);
        logger.info("profile with id: {} found via Profile API", profileId);

        profileRepository.save(profile);
        logger.info("profile saved  {} redis cache", profileId);
        return profile;
    }

}
