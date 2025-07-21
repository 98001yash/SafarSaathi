package com.company.SafarSaathi.companion_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompanionPreferenceRequest {

    private Integer preferredAgeMin;
    private Integer preferredAgeNax;
    private String preferredGender;
    private Boolean allowSmokers;
    private Boolean allowDrinkers;
    private String travelType;
}
