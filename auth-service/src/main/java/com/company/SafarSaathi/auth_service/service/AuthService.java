package com.company.SafarSaathi.auth_service.service;

import com.company.SafarSaathi.auth_service.client.UserServiceClient;
import com.company.SafarSaathi.auth_service.dtos.*;
import com.company.SafarSaathi.auth_service.entities.User;
import com.company.SafarSaathi.auth_service.enums.Role;
import com.company.SafarSaathi.auth_service.exceptions.BadRequestException;
import com.company.SafarSaathi.auth_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.auth_service.repository.UserRepository;
import com.company.SafarSaathi.auth_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;

    public UserProfileCreateRequest signUp(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new BadRequestException("User already exists");
        }

        User user = User.builder()
                .fullName(signupRequestDto.getFullName())
                .email(signupRequestDto.getEmail())
                .password(PasswordUtils.hashPassword(signupRequestDto.getPassword()))
                .role(Role.TRAVELLER)
                .build();

        User savedUser = userRepository.save(user);

        // Send basic profile creation request
        BasicProfileCreateRequest profileRequest = BasicProfileCreateRequest.builder()
                .userId(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .build();

        userServiceClient.createUser(profileRequest);

        return UserProfileCreateRequest.builder()
                .userId(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .build(); // Return basic profile info (optional)
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequestDto.getEmail()));

        if (!PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }
}
