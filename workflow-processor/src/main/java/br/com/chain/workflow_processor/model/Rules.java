package br.com.chain.workflow_processor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rules {

    private int id;
    private String description;
    private List<RulesItem> rules;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @Setter
    public static class RulesItem {
        private int id;
        private String description;
    }
}
