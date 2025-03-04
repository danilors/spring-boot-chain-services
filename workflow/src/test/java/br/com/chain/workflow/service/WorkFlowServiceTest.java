package br.com.chain.workflow.service;

import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class WorkFlowServiceTest {

    @Mock
    private ProfileService profileService;

    @Mock
    private TaskExecutorService taskExecutorService;

    @Mock
    private AddressService addressService;

    @Mock
    private OccupationService occupationService;

    @InjectMocks
    private WorkFlowService workFlowService;

    private Profile testProfile;
    private List<CompletableService> completableServices;
    private DataWorkflow dataWorkflow;

    @BeforeEach
    void setUp() {
        testProfile = new Profile("1", "Test User", "test@example.com", 1, 1);
        completableServices = new ArrayList<>();
        completableServices.add(addressService);
        completableServices.add(occupationService);
        dataWorkflow = new DataWorkflow(testProfile);
        workFlowService = new WorkFlowService(completableServices, profileService, taskExecutorService);
    }

    @Test
    void startTaskExecutor_Success() {
        when(profileService.getProfileById(anyInt())).thenReturn(testProfile);
        when(taskExecutorService.processTask(any(), any())).thenReturn(dataWorkflow);

        DataWorkflow result = workFlowService.startTaskExecutor(1);

        assertNotNull(result);
        assertEquals(dataWorkflow, result);
        verify(profileService, times(1)).getProfileById(1);
        verify(taskExecutorService, times(1)).processTask(testProfile, completableServices);
    }
    @Test
    void startParallelStream_Success() {
        when(profileService.getProfileById(anyInt())).thenReturn(testProfile);
        DataWorkflow result = workFlowService.startParallelStream(1);

        assertNotNull(result);
        verify(profileService, times(1)).getProfileById(1);
        verify(addressService, times(1)).run(testProfile, result);
        verify(occupationService, times(1)).run(testProfile, result);
    }
}