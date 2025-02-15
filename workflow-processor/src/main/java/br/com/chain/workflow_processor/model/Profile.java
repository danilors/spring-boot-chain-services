package br.com.chain.workflow_processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record Profile(Integer id, String name, String email) {

}
