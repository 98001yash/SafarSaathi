package com.company.SafarSaathi.matching_service.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MatchRequest {


    @JsonProperty("user_profile")
    private CompanionProfile userProfile;

    @JsonProperty("candidates")
    private List<CompanionProfile> candidates;

    public MatchRequest(CompanionProfile userProfile, List<CompanionProfile> candidates) {
        this.userProfile = userProfile;
        this.candidates = candidates;
    }
}