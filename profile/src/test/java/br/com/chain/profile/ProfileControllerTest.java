package br.com.chain.profile;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:test.sql")
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void getAllProfiles() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someEmail = UUID.randomUUID().toString().concat("@email.com");
        createOneProfile(buildProfile(someName, someEmail));

        mockMvc.perform(get("/api/profiles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(someName))
                .andExpect(jsonPath("$[0].email").value(someEmail));
    }

    @Test
    public void getProfileById() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someEmail = UUID.randomUUID().toString().concat("@email.com");
     var created =    createOneProfile(buildProfile(someName, someEmail));

        mockMvc.perform(get("/api/profiles/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(someName))
                .andExpect(jsonPath("$.email").value(someEmail));
    }

    @Test
    public void createProfile() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someEmail = UUID.randomUUID().toString().concat("@email.com");
        Profile profile = buildProfile(someName, someEmail);

        mockMvc.perform(post("/api/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(someName))
                .andExpect(jsonPath("$.email").value(someEmail));
    }

    @Test
    public void updateProfile() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someEmail = UUID.randomUUID().toString().concat("@email.com");
       var created =  createOneProfile(buildProfile(someName, someEmail));

        var updatedName = UUID.randomUUID().toString() + "updatedName";
        var updatedEmail = UUID.randomUUID().toString().concat("@updatedEmail.com");
        created.setName(updatedName);
        created.setEmail(updatedEmail);

        mockMvc.perform(put("/api/profiles/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.email").value(updatedEmail));
    }

    @Test
    public void deleteProfile() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someEmail = UUID.randomUUID().toString().concat("@email.com");
       var created = createOneProfile(buildProfile(someName, someEmail));

        mockMvc.perform(delete("/api/profiles/" + created.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/profiles/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getProfileById_NotFound() throws Exception {
        mockMvc.perform(get("/api/profiles/999")) // ID que não existe
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateProfile_NotFound() throws Exception {
        var updatedName = UUID.randomUUID().toString() + "updatedName";
        var updatedEmail = UUID.randomUUID().toString().concat("@updatedEmail.com");
        Profile updatedProfile = buildProfile(updatedName, updatedEmail);

        mockMvc.perform(put("/api/profiles/999") // ID que não existe
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProfile)))
                .andExpect(status().isNotFound());
    }


    private Profile createOneProfile(Profile profile) throws Exception {
       var result =  mockMvc.perform(post("/api/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profile))).andReturn();

      return objectMapper.readValue(result.getResponse().getContentAsString(), Profile.class);
    }

    private Profile buildProfile(String name, String email) {
        return Profile.builder()
                .name(name)
                .email(email)
                .addressId(1L)
                .occupationId(1L)
                .build();
    }
}