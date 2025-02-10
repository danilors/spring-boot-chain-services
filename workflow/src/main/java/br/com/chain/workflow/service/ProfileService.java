package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.model.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileClient profileClient;

    public ProfileService(ProfileClient profileClient) {
        this.profileClient = profileClient;
    }

    public List<Profile> getAllProfiles() {
        return profileClient.getAllProfiles();
    }
}
