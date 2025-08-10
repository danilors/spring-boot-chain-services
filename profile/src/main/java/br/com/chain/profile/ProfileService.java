package br.com.chain.profile;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long id, Profile profile) {
        Profile existingProfile = profileRepository.findById(id).orElse(null);

        if (existingProfile != null) {
            existingProfile.setName(profile.getName());
            existingProfile.setEmail(profile.getEmail());
            return profileRepository.save(existingProfile);
        }
        return null;
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}