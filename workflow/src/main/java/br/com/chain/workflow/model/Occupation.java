package br.com.chain.workflow.model;

public record Occupation(int id, String name, int code) {
    public static Occupation defaultOccupation() {
        return new Occupation(-1, "unknown", -1);
    }
}
