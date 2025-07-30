package com.company.SafarSaathi.matching_service.service;



import com.company.SafarSaathi.matching_service.client.PythonMatchingClient;
import com.company.SafarSaathi.matching_service.dtos.CompanionProfile;
import com.company.SafarSaathi.matching_service.dtos.MatchRequest;
import com.company.SafarSaathi.matching_service.dtos.PythonMatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final PythonMatchingClient pythonClient;

    public List<CompanionProfile> findTopMatches(CompanionProfile user, List<CompanionProfile> candidates) {
        MatchRequest request = new MatchRequest(user, candidates);
        List<PythonMatchResponse> responseList = pythonClient.getMatches(request);

        return responseList.stream()
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .map(PythonMatchResponse::getProfile)
                .collect(Collectors.toList());
    }
}
