package com.company.SafarSaathi.user_service.service;

import com.company.SafarSaathi.user_service.auth.UserContextHolder;
import com.company.SafarSaathi.user_service.dtos.UpdateUserRequest;
import com.company.SafarSaathi.user_service.dtos.UserProfileCreateRequest;
import com.company.SafarSaathi.user_service.entities.User;
import com.company.SafarSaathi.user_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // ✅ Get current user (from JWT/X-User-Id header)
    public UserProfileCreateRequest getCurrentUserProfile() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Fetching current user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return modelMapper.map(user, UserProfileCreateRequest.class);
    }

    // ✅ Get any user by userId (for matching-service or others)
    public UserProfileCreateRequest getUserById(Long userId) {
        log.info("Fetching user by ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return modelMapper.map(user, UserProfileCreateRequest.class);
    }

    // ✅ Update current user (from JWT)
    public UserProfileCreateRequest updateUser(UpdateUserRequest request) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Updating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Update fields
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        user.setAge(request.getAge());
        user.setBio(request.getBio());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        user.setSmoker(request.isSmoker());
        user.setDrinker(request.isDrinker());
        user.setLifestyle(request.getLifestyle());
        user.setTravelStyle(request.getTravelStyle());
        user.setProfileImageUrl(request.getProfileImageUrl());

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserProfileCreateRequest.class);
    }

    // ✅ Create user profile (called from auth-service on signup)
    public UserProfileCreateRequest createUser(UserProfileCreateRequest userDto) {
        log.info("Creating user with ID: {}", userDto.getUserId());
        User user = modelMapper.map(userDto, User.class);
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserProfileCreateRequest.class);
    }
}
