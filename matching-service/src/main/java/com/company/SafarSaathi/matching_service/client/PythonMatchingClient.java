package com.company.SafarSaathi.matching_service.client;


import com.company.SafarSaathi.matching_service.dtos.PythonMatchResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class PythonMatchingClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PYTHON_MATCH_URL = "http://localhost:8001/match";

    public List<PythonMatchResponse> getMatches(MatchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MatchRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PythonMatchResponse[]> response = restTemplate.postForEntity(
                PYTHON_MATCH_URL, entity, PythonMatchResponse[].class);

        return Arrays.asList(response.getBody());
    }
}