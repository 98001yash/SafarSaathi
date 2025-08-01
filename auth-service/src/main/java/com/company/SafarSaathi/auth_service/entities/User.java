package com.company.SafarSaathi.auth_service.entities;

import com.company.SafarSaathi.auth_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Optional if you want to store user's name
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.TRAVELLER; // Default role
}
