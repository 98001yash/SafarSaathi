package com.company.SafarSaathi.matching_service.service;


import com.company.SafarSaathi.matching_service.client.MatchingClient;
import com.company.SafarSaathi.matching_service.dtos.CompanionProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {


    private final MatchingClient matchingClient;

    public List<CompanionProfile> findTopMatches(CompanionProfile user, List<CompanionProfile> candidates) {
        MatchRequest request = new MatchRequest();
        request.setUserProfile(user);
        request.setCandidates(candidates);

        MatchResponse response = matchingClient.getTopMatches(request);

        return response.getMatches().stream()
                .map(result -> candidates.get(result.getIndex()))
                .collect(Collectors.toList());
    }
}
