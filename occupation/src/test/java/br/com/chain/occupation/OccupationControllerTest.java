package br.com.chain.occupation;


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
public class OccupationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void getAllOccupations() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someCode = new Random().nextInt();
        createOneOccupation(buildOccupation(someName, someCode));

        mockMvc.perform(get("/api/occupations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(someName))
                .andExpect(jsonPath("$[0].code").value(someCode));
    }

    @Test
    public void getOccupationById() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someCode = new Random().nextInt();
        var created = createOneOccupation(buildOccupation(someName, someCode));

        mockMvc.perform(get("/api/occupations/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(someName))
                .andExpect(jsonPath("$.code").value(someCode));
    }

    @Test
    public void createOccupation() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someCode = new Random().nextInt();
        Occupation occupation = buildOccupation(someName, someCode);

        mockMvc.perform(post("/api/occupations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(occupation)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(someName))
                .andExpect(jsonPath("$.code").value(someCode));
    }

    @Test
    public void updateOccupation() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someCode = new Random().nextInt();
        var created = createOneOccupation(buildOccupation(someName, someCode));

        var updatedName = UUID.randomUUID().toString() + "updatedName";
        var updatedCode  = new Random().nextInt();
        created.setName(updatedName);
        created.setCode(updatedCode);

        mockMvc.perform(put("/api/occupations/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.code").value(updatedCode));
    }

    @Test
    public void deleteOccupation() throws Exception {
        var someName = UUID.randomUUID().toString() + "somename";
        var someCode = new Random().nextInt();
        var created = createOneOccupation(buildOccupation(someName, someCode));

        mockMvc.perform(delete("/api/occupations/" + created.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/occupations/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOccupationById_NotFound() throws Exception {
        mockMvc.perform(get("/api/occupations/999")) // ID que não existe
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateOccupation_NotFound() throws Exception {
        var updatedName = UUID.randomUUID().toString() + "updatedName";
        var updatedCode = new Random().nextInt();
        Occupation updatedOccupation = buildOccupation(updatedName, updatedCode);

        mockMvc.perform(put("/api/occupations/999") // ID que não existe
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOccupation)))
                .andExpect(status().isNotFound());
    }


    private Occupation createOneOccupation(Occupation occupation) throws Exception {
        var result = mockMvc.perform(post("/api/occupations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(occupation))).andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Occupation.class);
    }

    private Occupation buildOccupation(String name, Integer code) {
        return Occupation.builder()
                .name(name)
                .code(code)
                .build();
    }
}