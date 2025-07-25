package com.company.SafarSaathi.matching_service.service;


import com.company.SafarSaathi.matching_service.auth.UserContextHolder;
import com.company.SafarSaathi.matching_service.client.CompanionClient;
import com.company.SafarSaathi.matching_service.client.TripClient;
import com.company.SafarSaathi.matching_service.client.UserClient;
import com.company.SafarSaathi.matching_service.dtos.CompanionDto;
import com.company.SafarSaathi.matching_service.dtos.MatchResultDto;
import com.company.SafarSaathi.matching_service.dtos.TripDto;
import com.company.SafarSaathi.matching_service.dtos.UserProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchingService {

    private final CompanionClient companionClient;
    private final UserClient userClient;
    private final TripClient tripClient;


    public List<MatchResultDto> findMatchesForCurrentUser() {
        Long userId = UserContextHolder.getCurrentUserId();

        List<CompanionDto> companions = companionClient.getAllCompanions();
        CompanionDto currentUser = companions.stream()
                .filter(c -> c.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found in companions"));

        List<MatchResultDto> results = new ArrayList<>();
        UserProfileDto userProfile = userClient.getUserProfile(userId);
        TripDto userTrip = tripClient.getTripById(currentUser.getTripId());

        for (CompanionDto other : companions) {
            if (other.getUserId().equals(userId)) continue;

            UserProfileDto otherProfile = userClient.getUserProfile(other.getUserId());
            TripDto otherTrip = tripClient.getTripById(other.getTripId());

            if (isCompatible(userProfile, userTrip, otherProfile, otherTrip)) {
                results.add(new MatchResultDto(userId, other.getUserId()));
            }
        }

        return results;
    }

    private boolean isCompatible(UserProfileDto a, TripDto aTrip, UserProfileDto b, TripDto bTrip) {
        return aTrip.getId().equals(bTrip.getId()) && aTrip.getModeOfTravel() == bTrip.getModeOfTravel();
    }
}