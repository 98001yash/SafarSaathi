package com.company.SafarSaathi.companion_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequest {
    private UserProfileCreateRequest userProfileCreateRequest;
    private CompanionPreferenceDto userPreference;
    private TripDto trip;

    private List<CandidateProfile> candidates;
}
