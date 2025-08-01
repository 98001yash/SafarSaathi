package com.company.SafarSaathi.auth_service.client;


import com.company.SafarSaathi.auth_service.dtos.BasicProfileCreateRequest;
import com.company.SafarSaathi.auth_service.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @PostMapping("/users")
    UserDto createUser(@RequestBody BasicProfileCreateRequest request);
}

