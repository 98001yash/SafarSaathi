package com.company.SafarSaathi.user_service.controller;



import com.company.SafarSaathi.user_service.dtos.LoginRequestDto;
import com.company.SafarSaathi.user_service.dtos.SignupRequestDto;
import com.company.SafarSaathi.user_service.dtos.UserDto;
import com.company.SafarSaathi.user_service.service.AuthService;
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
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto){
        UserDto userDto = authService.signUp(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        String token = authService.login(loginRequestDto);

        return ResponseEntity.ok(token);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("X-User-Id") String userId) {
        UserDto userDto = authService.getUserProfile(Long.parseLong(userId));
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateUserProfile(@RequestHeader("X-User-Id") String userId,
                                                     @RequestBody UserDto updatedUserDto) {
        UserDto userDto = authService.updateUserProfile(Long.parseLong(userId), updatedUserDto);
        return ResponseEntity.ok(userDto);
    }
}
