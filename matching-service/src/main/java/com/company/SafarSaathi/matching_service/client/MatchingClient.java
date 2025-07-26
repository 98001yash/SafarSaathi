package com.company.SafarSaathi.matching_service.client;


import com.company.SafarSaathi.matching_service.dtos.MatchRequest;
import com.company.SafarSaathi.matching_service.dtos.MatchResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MatchingClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String mlServiceUrl = "http://localhost:8001/match";

    public MatchResponse getTopMatches(MatchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MatchRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<MatchResponse> response = restTemplate.postForEntity(mlServiceUrl, entity, MatchResponse.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to call ML Matching Service");
        }
    }
}
