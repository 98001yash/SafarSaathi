package com.company.SafarSaathi.user_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    private String fullName;
    private String phoneNumber;

    private String gender;

    private int age;
    private String bio;
    private String country;
    private String city;

    private boolean smoker;
    private boolean drinker;

    private String lifeStyle;

    private String travelStyle;

    private String profileImageUrl;
}
