package com.company.SafarSaathi.user_service.service;



import com.company.SafarSaathi.user_service.dtos.LoginRequestDto;
import com.company.SafarSaathi.user_service.dtos.SignupRequestDto;
import com.company.SafarSaathi.user_service.dtos.UserDto;
import com.company.SafarSaathi.user_service.entities.User;
import com.company.SafarSaathi.user_service.exceptions.BadRequestException;
import com.company.SafarSaathi.user_service.exceptions.ResourceNotFoundException;
import com.company.SafarSaathi.user_service.repository.UserRepository;
import com.company.SafarSaathi.user_service.utils.PasswordUtils;
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

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists){
            throw new BadRequestException("User already exists, cannot signup again");
        }
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(PasswordUtils.hashPassword(signupRequestDto.getPassword()));


        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch = PasswordUtils.checkPassword(loginRequestDto.getPassword(),user.getPassword());

        if(!isPasswordMatch){
            throw new BadRequestException("incorrect password");
        }
        return jwtService.generateAccessToken(user);
    }


    public UserDto getUserProfile(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with ID: "+userId));
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUserProfile(Long userId, UserDto updatedUserDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setName(updatedUserDto.getName());
        user.setEmail(updatedUserDto.getEmail());
        user.setAge(updatedUserDto.getAge());
        user.setGender(updatedUserDto.getGender());
        user.setSmoker(updatedUserDto.getSmoker());
        user.setDrinker(updatedUserDto.getDrinker());

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

}
