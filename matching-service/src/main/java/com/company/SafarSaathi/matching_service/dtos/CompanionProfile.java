package com.company.SafarSaathi.matching_service.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanionProfile {

    @JsonProperty("age")
    private int age;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("smoker")
    private boolean smoker;

    @JsonProperty("drinker")
    private boolean drinker;

    @JsonProperty("travel_type")
    private String travelType;

    @JsonProperty("trip_mode")
    private String tripMode;

}
