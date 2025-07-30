package com.company.SafarSaathi.companion_service.client;


import com.company.SafarSaathi.companion_service.dtos.CompanionProfile;
import com.company.SafarSaathi.companion_service.dtos.MatchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "matching-service",path = "/matching/match")
public interface MatchingClient {


    @PostMapping("/top-matches")
    List<CompanionProfile> getTopMatches(@RequestBody MatchRequest request);
}
