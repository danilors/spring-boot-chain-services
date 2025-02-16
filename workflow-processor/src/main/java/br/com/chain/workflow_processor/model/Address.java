package br.com.chain.workflow_processor.model;


public record Address(
        int id,
        String street,
        int number
) {
    public static Address defaulAddres() {
        return new Address(-1, "Unknown", 0);
    }

}

