package com.company.SafarSaathi.auth_service.controller;

import com.company.SafarSaathi.auth_service.dtos.LoginRequestDto;
import com.company.SafarSaathi.auth_service.dtos.SignupRequestDto;
import com.company.SafarSaathi.auth_service.dtos.UserProfileCreateRequest;
import com.company.SafarSaathi.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserProfileCreateRequest> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        UserProfileCreateRequest profile = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
