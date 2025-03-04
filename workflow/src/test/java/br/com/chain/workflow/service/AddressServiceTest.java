package br.com.chain.workflow.service;


import br.com.chain.workflow.clients.AddressClient;
import br.com.chain.workflow.model.Address;
import br.com.chain.workflow.model.DataWorkflow;
import br.com.chain.workflow.model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressClient addressClient;

    @InjectMocks
    private AddressService addressService;

    private Address testAddress;
    private Profile testProfile;
    private DataWorkflow dataWorkflow;

    @BeforeEach
    void setUp() {
        testAddress = new Address(1, "Test Street", 12345);
        testProfile = new Profile("1", "Test User", "test@example.com", 1, 1);
        dataWorkflow = new DataWorkflow(testProfile);
    }

    @Test
    void getAllAddress_Success() {
        when(addressClient.getAddressById(anyInt())).thenReturn(testAddress);

        Address result = addressService.getAllAddress(1);

        assertNotNull(result);
        assertEquals(testAddress, result);
        verify(addressClient, times(1)).getAddressById(1);
    }

    @Test
    void run_Success() {
        when(addressClient.getAddressById(anyInt())).thenReturn(testAddress);

        addressService.run(testProfile, dataWorkflow);

        assertNotNull(dataWorkflow.getAddress());
        assertEquals(testAddress, dataWorkflow.getAddress());
        verify(addressClient, times(1)).getAddressById(1);
    }
}