package com.company.SafarSaathi.matching_service.client;


import com.company.SafarSaathi.matching_service.dtos.CompanionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "companion-service", path = "/api/v2/companion/core")
public interface CompanionClient {

    @GetMapping
    List<CompanionDto> getAllCompanions();
}
