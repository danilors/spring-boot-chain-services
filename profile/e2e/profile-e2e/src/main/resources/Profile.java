package br.com.e2e.profiles.e2e.model;

public class Profile {
    private Long id;
    private String name;
    private String email;

    public Profile() {
    }

    public Profile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}