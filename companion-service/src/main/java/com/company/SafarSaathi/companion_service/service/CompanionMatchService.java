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

    public List<CompanionProfile> getTopMatches(Long tripId) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("[MATCH] Starting match process for userId: {}", userId);

        // 1. Fetch current user profile (no param)
        UserProfileCreateRequest userProfile = userClient.getCurrentUserProfile();
        if (userProfile == null) {
            throw new ResourceNotFoundException("User profile not found for userId: " + userId);
        }

        // 2. Fetch user preferences
        CompanionPreference preference = preferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Preference not found for userId: " + userId));
        CompanionPreferenceDto preferenceDto = modelMapper.map(preference, CompanionPreferenceDto.class);

        // 3. Fetch trip details
        TripDto trip = tripClient.getTripById(tripId);
        if (trip == null) {
            throw new ResourceNotFoundException("Trip not found for tripId: " + tripId);
        }

        // 4. Fetch all open companions
        List<Companion> candidates = companionRepository.findByStatus("OPEN");
        List<CandidateProfile> candidateProfiles = new ArrayList<>();

        for (Companion c : candidates) {
            if (c.getUserId().equals(userId)) {
                log.debug("[MATCH] Skipping self profile: userId={}", c.getUserId());
                continue;
            }

            // Fetch candidate user profile
            UserProfileCreateRequest candidateUserProfile = userClient.getUserProfileByUserId(c.getUserId());
            if (candidateUserProfile == null) {
                log.warn("[MATCH] Candidate user profile missing for userId: {}", c.getUserId());
                continue;
            }

            // Fetch candidate preferences
            CompanionPreferenceDto candidatePref = preferenceRepository.findByUserId(c.getUserId())
                    .map(p -> modelMapper.map(p, CompanionPreferenceDto.class))
                    .orElse(null);

            if (candidatePref == null) {
                log.warn("[MATCH] Candidate preference missing for userId: {}", c.getUserId());
            }

            // Add to list
            candidateProfiles.add(CandidateProfile.builder()
                    .companion(modelMapper.map(c, CompanionDto.class))
                    .userProfileCreateRequest(candidateUserProfile)
                    .preference(candidatePref)
                    .build());
        }

        // 5. Build MatchRequest
        MatchRequest matchRequest = MatchRequest.builder()
                .userProfileCreateRequest(userProfile)
                .userPreference(preferenceDto)
                .trip(trip)
                .candidates(candidateProfiles)
                .build();

        log.info("[MATCH] Sending MatchRequest to matching-service with {} candidates", candidateProfiles.size());

        // 6. Call ML Matching Service
        List<CompanionProfile> matches = matchingClient.getTopMatches(matchRequest);
        log.info("[MATCH] Received {} matches from matching-service", matches.size());

        return matches;
    }
}
