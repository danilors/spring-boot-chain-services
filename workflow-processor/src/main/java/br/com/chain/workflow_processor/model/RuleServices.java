package br.com.chain.workflow_processor.model;

import br.com.chain.workflow_processor.enums.ServiceNamesEnum;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RuleServices {
    List<ServiceNamesEnum> services;
}
