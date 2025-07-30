package com.company.SafarSaathi.companion_service.service;


import com.company.SafarSaathi.companion_service.auth.UserContextHolder;
import com.company.SafarSaathi.companion_service.client.MatchingClient;
import com.company.SafarSaathi.companion_service.client.TripClient;
import com.company.SafarSaathi.companion_service.client.UserClient;
import com.company.SafarSaathi.companion_service.dtos.*;
import com.company.SafarSaathi.companion_service.entity.Companion;
import com.company.SafarSaathi.companion_service.entity.CompanionPreference;
import com.company.SafarSaathi.companion_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.companion_service.repository.CompanionPreferenceRepository;
import com.company.SafarSaathi.companion_service.repository.CompanionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanionMatchService {

    private final CompanionRepository companionRepository;
    private final CompanionPreferenceRepository preferenceRepository;
    private final ModelMapper modelMapper;

    private final MatchingClient matchingClient;
    private final TripClient tripClient;
    private final UserClient userClient;

    public List<CompanionDto> getTopMatches(Long userId, Long tripId) {


        // 1. Fetch UserProfile
        UserProfile userProfile = userClient.getUserProfile(userId);



        // 2. Fetch User Preference
        CompanionPreference preference = preferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Preference not found"));
        CompanionPreferenceDto preferenceDto = modelMapper.map(preference, CompanionPreferenceDto.class);


        // 3. Fetch Trip Details
        TripDto trip = tripClient.getTripById(tripId);


        // 4. Fetch All Candidates
        List<Companion> candidates = companionRepository.findByStatus("OPEN");
        List<CandidateProfile> candidateProfiles = new ArrayList<>();

        for (Companion c : candidates) {
            if (c.getUserId().equals(userId)) continue; // Skip self

            UserProfile candidateProfile = userClient.getUserProfile(c.getUserId());
            CompanionPreferenceDto candidatePref = preferenceRepository.findByUserId(c.getUserId())
                    .map(p -> modelMapper.map(p, CompanionPreferenceDto.class))
                    .orElse(null);

            candidateProfiles.add(CandidateProfile.builder()
                    .companion(modelMapper.map(c, CompanionDto.class))
                    .userProfile(candidateProfile)
                    .preference(candidatePref)
                    .build());
        }



        // 5. Create MatchRequest
        MatchRequest request = MatchRequest.builder()
                .userProfile(userProfile)
                .userPreference(preferenceDto)
                .trip(trip)
                .candidates(candidateProfiles)
                .build();



        // 6. Call Matching Service
        return matchingClient.getTopMatches(request);
    }
}