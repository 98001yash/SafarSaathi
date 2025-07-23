package com.company.SafarSaathi.companion_service.client;

import com.company.SafarSaathi.companion_service.dtos.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/auth/{id}/profile")
    UserProfile getUserProfile(@PathVariable("id") Long id);
}
