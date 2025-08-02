package com.company.SafarSaathi.companion_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateProfile {


    private CompanionDto companion;
    private UserProfileCreateRequest userProfileCreateRequest;
    private CompanionPreferenceDto preference;
}
