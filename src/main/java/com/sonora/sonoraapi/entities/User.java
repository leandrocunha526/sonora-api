package com.sonora.sonoraapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
@ToString(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 60, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private Role role = Role.CLIENT;
}