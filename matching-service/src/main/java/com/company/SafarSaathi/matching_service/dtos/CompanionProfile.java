package com.company.SafarSaathi.matching_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanionProfile {

    private int age;
    private String gender;
    private boolean smoker;
    private boolean drinker;
    private String travelType;
    private String tripMode;
}
