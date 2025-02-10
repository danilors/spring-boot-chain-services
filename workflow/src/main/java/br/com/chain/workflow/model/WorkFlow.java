package br.com.chain.workflow.model;


import java.util.List;

public record WorkFlow(List<Profile> profiles, List<Address> addresses) {

}
