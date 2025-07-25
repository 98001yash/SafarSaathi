package com.company.SafarSaathi.matching_service.controller;


import com.company.SafarSaathi.matching_service.dtos.MatchResultDto;
import com.company.SafarSaathi.matching_service.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;


    @GetMapping("/user/{userId}")
    public List<MatchResultDto> getMatches(@PathVariable Long userId){
        return matchingService.findMatchesForUser(userId);
    }
}
