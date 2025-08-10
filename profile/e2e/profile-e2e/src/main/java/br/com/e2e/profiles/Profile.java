package br.com.e2e.profiles;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Profile(Long id, String name, String email, Long addressId, Long occupationId) {
    public Profile(String name, String email, Long addressId, Long occupationId){
        this(null, name, email, addressId, occupationId);
    }
}