package com.company.SafarSaathi.auth_service.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileCreateRequest {
    private Long userId;
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