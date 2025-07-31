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

        // 1. Authenticate the User
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("[MATCH] Fetching top matches for userId: {}", userId);

        // 2. Fetch UserProfile
        log.info("[MATCH] Fetching user profile for userId: {}", userId);
        UserProfile userProfile = userClient.getUserProfile(userId);
        log.info("[MATCH] User profile fetched: {}", userProfile);

        // 3. Fetch User Preference
        log.info("[MATCH] Fetching preferences for userId: {}", userId);
        CompanionPreference preference = preferenceRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("[MATCH] No preferences found for userId: {}", userId);
                    return new ResourceNotFoundException("Preference not found");
                });
        CompanionPreferenceDto preferenceDto = modelMapper.map(preference, CompanionPreferenceDto.class);
        log.info("[MATCH] User preference fetched: {}", preferenceDto);

        // 4. Fetch Trip Details
        log.info("[MATCH] Fetching trip by tripId: {}", tripId);
        TripDto trip = tripClient.getTripById(tripId);
        log.info("[MATCH] Trip fetched: {}", trip);

        // 5. Fetch All Open Companion Candidates
        log.info("[MATCH] Fetching all open companions...");
        List<Companion> candidates = companionRepository.findByStatus("OPEN");
        log.info("[MATCH] Total open companions fetched: {}", candidates.size());

        List<CandidateProfile> candidateProfiles = new ArrayList<>();

        for (Companion c : candidates) {
            if (c.getUserId().equals(userId)) {
                log.info("[MATCH] Skipping self candidate with userId: {}", c.getUserId());
                continue;
            }

            log.info("[MATCH] Fetching candidate user profile for userId: {}", c.getUserId());
            UserProfile candidateProfile = userClient.getUserProfile(c.getUserId());
            log.info("[MATCH] Candidate profile fetched: {}", candidateProfile);

            CompanionPreferenceDto candidatePref = preferenceRepository.findByUserId(c.getUserId())
                    .map(p -> {
                        log.info("[MATCH] Candidate preference found for userId: {}", c.getUserId());
                        return modelMapper.map(p, CompanionPreferenceDto.class);
                    })
                    .orElse(null);

            if (candidatePref == null) {
                log.warn("[MATCH] No preferences found for candidate userId: {}", c.getUserId());
            }

            candidateProfiles.add(CandidateProfile.builder()
                    .companion(modelMapper.map(c, CompanionDto.class))
                    .userProfile(candidateProfile)
                    .preference(candidatePref)
                    .build());
        }

        // 6. Construct Match Request
        MatchRequest request = MatchRequest.builder()
                .userProfile(userProfile)
                .userPreference(preferenceDto)
                .trip(trip)
                .candidates(candidateProfiles)
                .build();

        log.info("[MATCH] MatchRequest built. Sending to matching-service: {}", request);

        // 7. Call Matching Service
        List<CompanionProfile> matches = matchingClient.getTopMatches(request);
        log.info("[MATCH] Received {} matches from matching-service", matches.size());

        return matches;
    }
}
