package br.com.chain.address;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:test.sql")
public class AddressControllerTest {

    private static final String ADDRESS_API_ENDPOINT = "/api/address";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void getAllAddress() throws Exception {
        var street = "Main Street";
        var someNumber = new Random().nextInt();
        createOneAddress(buildAddress(street, someNumber));

        mockMvc.perform(get(ADDRESS_API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].street").value(street))
                .andExpect(jsonPath("$[0].number").value(someNumber));
    }

    @Test
    public void getAddressById() throws Exception {
        var street = "Main Street";
        var someNumber = new Random().nextInt();
        var created = createOneAddress(buildAddress(street, someNumber));

        mockMvc.perform(get(ADDRESS_API_ENDPOINT + "/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street").value(street))
                .andExpect(jsonPath("$.number").value(someNumber));
    }

    @Test
    public void createAddress() throws Exception {
        var street = "Main Street";
        var someNumber = new Random().nextInt();
        Address address = buildAddress(street, someNumber);

        mockMvc.perform(post(ADDRESS_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street").value(street))
                .andExpect(jsonPath("$.number").value(someNumber));
    }

    @Test
    public void updateAddress() throws Exception {
        var street = "Main Street";
        var someNumber = new Random().nextInt();
        var created = createOneAddress(buildAddress(street, someNumber));

        var updatedStreet = UUID.randomUUID().toString() + "updatedStreet";
        var number = new Random().nextInt();
        created.setStreet(updatedStreet);
        created.setNumber(number);

        mockMvc.perform(put(ADDRESS_API_ENDPOINT + "/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street").value(updatedStreet))
                .andExpect(jsonPath("$.number").value(number));
    }

    @Test
    public void deleteAddress() throws Exception {
        var street = "Main Street";
        var number = new Random().nextInt();
        var created = createOneAddress(buildAddress(street, number));

        mockMvc.perform(delete(ADDRESS_API_ENDPOINT + "/" + created.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get(ADDRESS_API_ENDPOINT + "/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAddressById_NotFound() throws Exception {
        mockMvc.perform(get(ADDRESS_API_ENDPOINT + "/999")) // ID que não existe
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAddress_NotFound() throws Exception {
        var updatedStreet = UUID.randomUUID().toString() + "updatedStreet";
        var number = new Random().nextInt();
        Address updatedAddress = buildAddress(updatedStreet, number);

        mockMvc.perform(put(ADDRESS_API_ENDPOINT + "/999") // ID que não existe
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAddress)))
                .andExpect(status().isNotFound());
    }

    private Address createOneAddress(Address address) throws Exception {
        var result = mockMvc.perform(post(ADDRESS_API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(address))).andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Address.class);
    }

    private Address buildAddress(String street, Integer number) {
        return Address.builder()
                .street(street)
                .number(number)
                .build();
    }
}