package br.com.chain.workflow.model;

public record Profile(Long id, String name, String email, int addressId, int occupationId) {
}
