package com.company.SafarSaathi.Bot_service.service;


import com.company.SafarSaathi.Bot_service.dtos.BotQueryRequest;
import com.company.SafarSaathi.Bot_service.dtos.BotQueryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService {

    private final RestTemplate restTemplate;


    @Value("${ml.service.url}")
    private String mlServiceUrl;


    public BotQueryResponseDto processQuery(Long userId, BotQueryRequest request){
        log.info("Sending query to ML service: {}",request.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,Object> body = new HashMap<>();
        body.put("userId",userId);
        body.put("message",request.getMessage());
        body.put("tripId",request.getTripId());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<BotQueryResponseDto> response = restTemplate
                .postForEntity(mlServiceUrl + "/bot/respond", entity, BotQueryResponseDto.class);

        return response.getBody();
    }
}
