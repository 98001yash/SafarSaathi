package com.company.SafarSaathi.matching_service.client;

import com.company.SafarSaathi.matching_service.dtos.MatchRequest;
import com.company.SafarSaathi.matching_service.dtos.PythonMatchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PythonMatchingClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PYTHON_MATCH_URL = "http://localhost:8001/match";

    private static final Logger logger = Logger.getLogger(PythonMatchingClient.class.getName());

    public List<PythonMatchResponse> getMatches(MatchRequest request) {
        try {
            // Log outgoing JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(request);
            logger.info("Sending JSON to Python service:\n" + json);
        } catch (Exception e) {
            logger.warning("Failed to serialize request: " + e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MatchRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<PythonMatchResponse[]> response = restTemplate.postForEntity(
                PYTHON_MATCH_URL, entity, PythonMatchResponse[].class);

        return Arrays.asList(response.getBody());

        /*
         AI Matching and recommendation system
         */
    }
}
