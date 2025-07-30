package com.company.SafarSaathi.matching_service.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequest {


    @JsonProperty("profile")
    private CompanionProfile userProfile;
    public List<CompanionProfile> candidates;
}
