package br.com.chain.workflow_processor.model;


public record Address(
        Integer id,
        String street,
        Integer number
) {
    public static Address defaulAddres() {
        return new Address(-1, "Unknown", 0);
    }

}

