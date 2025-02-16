package br.com.chain.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:test.sql")
public class RulesControllerTest {

    private static final String RULES_API_ENDPOINT = "/api/rules";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void getAllRules() throws Exception {
        Rules rules = buildRulesWithRuleItems("Test Rules", List.of("Rule 1", "Rule 2"));
        createOneRules(rules);

        mockMvc.perform(get(RULES_API_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].rules[0].description").value("Rule 1"));
    }

    @Test
    public void getRulesById() throws Exception {
        Rules rules = buildRulesWithRuleItems("Test Rules", List.of("Rule 1", "Rule 2"));
        Rules createdRules = createOneRules(rules);

        mockMvc.perform(get(RULES_API_ENDPOINT + "/" + createdRules.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rules[0].description").value("Rule 1"));
    }

    @Test
    public void createRules() throws Exception {
        Rules rules = buildRulesWithRuleItems("Test Rules", List.of("Rule 1", "Rule 2"));

        mockMvc.perform(post(RULES_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rules)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rules[0].description").value("Rule 1"));
    }


    @Test
    public void updateRules() throws Exception {
        Rules rules = buildRulesWithRuleItems("Test Rules", List.of("Rule 1", "Rule 2"));
        Rules createdRules = createOneRules(rules);

        createdRules.setDescription("Updated Rule 1");
        createdRules.setCreated(LocalDate.now());

        mockMvc.perform(put(RULES_API_ENDPOINT + "/" + createdRules.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdRules)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Updated Rule 1"));
    }


    @Test
    public void deleteRules() throws Exception {
        Rules rules = buildRulesWithRuleItems("Test Rules", List.of("Rule 1"));
        Rules createdRules = createOneRules(rules);

        mockMvc.perform(delete(RULES_API_ENDPOINT + "/" + createdRules.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get(RULES_API_ENDPOINT + "/" + createdRules.getId()))
                .andExpect(status().isNotFound());
    }



    private Rules createOneRules(Rules rules) throws Exception {
        var result = mockMvc.perform(post(RULES_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rules)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Rules.class);
    }

    private Rules buildRulesWithRuleItems(String descriptionValue, List<String> ruleDescriptions) {
        Rules rules = new Rules();
        rules.setDescription(descriptionValue);
        rules.setCreated(LocalDate.now());
        List<RuleItem> ruleItems = ruleDescriptions.stream()
                .map(description -> {
                    RuleItem item = new RuleItem();
                    item.setDescription(description);
                    return item;
                }).toList();
        rules.setRules(ruleItems);
        return rules;
    }

}