package com.company.SafarSaathi.companion_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanionPreferenceDto {


    private Long id;
    private Long userId;

    private Integer preferredAgeMin;
    private Integer preferredAgeMax;

    private String preferredGender;

    private Boolean allowSmokers;
    private Boolean allowDrinkers;

    private String travelType;

}
