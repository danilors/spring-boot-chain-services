package br.com.chain.profile;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private int addressId;

    @Column(name = "occupation", nullable = false)
    private int occupationId;
}