package br.com.chain.workflow.service;

import br.com.chain.workflow.clients.ProfileClient;
import br.com.chain.workflow.exception.ProfileNotFoundException;
import br.com.chain.workflow.model.Profile;
import br.com.chain.workflow.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileClient profileClient;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private Profile testProfile;

    @BeforeEach
    void setUp() {
        testProfile = new Profile("1", "Test User", "test@example.com", 1, 1);
    }

    @Test
    void getProfileById_ProfileFoundInCache() {
        when(profileRepository.findById(anyString())).thenReturn(Optional.of(testProfile));

        Profile result = profileService.getProfileById(1);

        assertNotNull(result);
        assertEquals(testProfile, result);
        verify(profileRepository, times(1)).findById("1");
        verify(profileClient, never()).getProfileById(anyInt());
    }

    @Test
    void getProfileById_ProfileNotFoundInCache_FoundInClient() {
        when(profileRepository.findById(anyString())).thenReturn(Optional.empty());
        when(profileClient.getProfileById(anyInt())).thenReturn(testProfile);
        when(profileRepository.save(any())).thenReturn(testProfile);

        Profile result = profileService.getProfileById(1);

        assertNotNull(result);
        assertEquals(testProfile, result);
        verify(profileRepository, times(1)).findById("1");
        verify(profileClient, times(1)).getProfileById(1);
        verify(profileRepository, times(1)).save(testProfile);
    }

    @Test
    void getProfileById_ProfileNotFoundInCache_NotFoundInClient() {
        when(profileRepository.findById(anyString())).thenReturn(Optional.empty());
        when(profileClient.getProfileById(anyInt())).thenThrow(new ProfileNotFoundException("Profile not found"));


        var exception = assertThrows(ProfileNotFoundException.class, () -> {
            profileService.getProfileById(1);
        });

        assertEquals("Profile not found", exception.getMessage());
        verify(profileRepository, times(1)).findById("1");
        verify(profileClient, times(1)).getProfileById(1);
        verify(profileRepository, never()).save(any());
    }

    @Test
    void getProfileById_profileIdToStringConversion() {
        //Arrange
        when(profileRepository.findById(anyString())).thenReturn(Optional.of(testProfile));
        //Act
        profileService.getProfileById(1);

        //Assert
        verify(profileRepository, times(1)).findById("1");
    }
}