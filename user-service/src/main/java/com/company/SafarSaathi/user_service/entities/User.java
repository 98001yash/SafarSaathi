package com.company.SafarSaathi.user_service.entities;


import com.company.SafarSaathi.user_service.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {


    @Id
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private int age;
    private String bio;
    private String country;
    private String city;

    private boolean smoker;
    private boolean drinker;
    private String lifestyle;
    private String travelStyle;
    private String profileImageUrl;
}
