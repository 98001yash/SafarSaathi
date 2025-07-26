package com.company.SafarSaathi.matching_service.controller;


import com.company.SafarSaathi.matching_service.dtos.CompanionProfile;
import com.company.SafarSaathi.matching_service.dtos.MatchRequest;
import com.company.SafarSaathi.matching_service.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping
    public List<CompanionProfile> getMatches(@RequestBody MatchRequest request){
        return matchingService.findTopMatches(request.getUserProfile(), request.getCandidates());
    }
}
