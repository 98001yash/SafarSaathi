package com.company.SafarSaathi.user_service.controller;


import com.company.SafarSaathi.user_service.dtos.UpdateUserRequest;
import com.company.SafarSaathi.user_service.dtos.UserProfileCreateRequest;
import com.company.SafarSaathi.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileCreateRequest> getUserProfile(){
        log.info("Getting user profile...");
        UserProfileCreateRequest userDto = userService.getUserById();
        return ResponseEntity.ok(userDto);

    }
    @PutMapping("/profile")
    public ResponseEntity<UserProfileCreateRequest> updateUserProfile(@RequestBody UpdateUserRequest request) {
        log.info("updating the user profile");
        UserProfileCreateRequest updated = userService.updateUser(request);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfileCreateRequest> createUser(@RequestBody UserProfileCreateRequest userDto) {
        log.info("Creating user profile....");
        UserProfileCreateRequest created = userService.createUser(userDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
