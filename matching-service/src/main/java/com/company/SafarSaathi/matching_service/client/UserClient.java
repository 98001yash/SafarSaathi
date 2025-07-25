package com.company.SafarSaathi.matching_service.client;


import com.company.SafarSaathi.matching_service.dtos.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/auth")
public interface UserClient {


    @GetMapping("/{id}/profile")
    UserProfileDto getUserProfile(@PathVariable("id") Long userId);
}
