package com.company.SafarSaathi.companion_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanionPreferenceDto {

    private Integer preferredAgeMin;
    private Integer preferredAgeMax;

    private String preferredGender;
    private String travelType;

    private Boolean smokerOk;
    private Boolean drinkerOk;

    private String preferredTripMode;

}
