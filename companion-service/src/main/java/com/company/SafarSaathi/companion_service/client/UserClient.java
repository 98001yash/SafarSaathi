package com.company.SafarSaathi.companion_service.client;


import com.company.SafarSaathi.companion_service.dtos.UserProfileCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserClient {


    @GetMapping("/users/profile")
    UserProfileCreateRequest getCurrentUserProfile(); // current user from X-User-Id header

    @GetMapping("/users/profile/{userId}")
    UserProfileCreateRequest getUserProfileByUserId(@PathVariable("userId") Long userId); // for candidates
}
