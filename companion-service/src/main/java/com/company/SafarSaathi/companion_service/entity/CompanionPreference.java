package com.company.SafarSaathi.companion_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companion_preferences")
@Builder
public class CompanionPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long userId;

    private Integer preferredAgeMin;
    private Integer preferredAgeMax;

    private String preferredGender;

    private Boolean allowSmokers;
    private Boolean allowDrinkers;

    private String travelType ;
}
