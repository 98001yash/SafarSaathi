package com.company.SafarSaathi.user_service.controller;


import com.company.SafarSaathi.user_service.dtos.UpdateUserRequest;
import com.company.SafarSaathi.user_service.dtos.UserDto;
import com.company.SafarSaathi.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(){
        log.info("Getting user profile...");
        UserDto userDto = userService.getUserById();
        return ResponseEntity.ok(userDto);

    }
    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateUserProfile(@RequestBody UpdateUserRequest request) {
        log.info("updating the user profile");
        UserDto updated = userService.updateUser(request);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Creating user profile....");
        UserDto created = userService.createUser(userDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
