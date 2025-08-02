package com.company.SafarSaathi.companion_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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