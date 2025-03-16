package br.com.chain.workflow.model;

public record Address(int id, String street, Integer number) {
    public static Address defaultAddress(){
        return new Address(-1, "unknow", -1);
    }
}
