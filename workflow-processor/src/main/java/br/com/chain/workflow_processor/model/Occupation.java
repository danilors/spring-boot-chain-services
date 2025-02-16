package br.com.chain.workflow_processor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record Occupation(int id, String name, int code) {

    public static Occupation defaultOccupation() {
        return new Occupation(-1, "Unknown", -1);
    }
}
