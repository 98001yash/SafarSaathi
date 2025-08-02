package com.company.SafarSaathi.matching_service.client;


import com.company.SafarSaathi.matching_service.dtos.UserDto;
import com.company.SafarSaathi.matching_service.dtos.UserProfileCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {


    @GetMapping("/users/profile")
    UserProfileCreateRequest getUserProfile();
}
