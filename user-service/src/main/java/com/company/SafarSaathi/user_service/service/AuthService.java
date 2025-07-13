package com.company.SafarSaathi.user_service.service;


import com.company.SafarSaathi.user_service.dtos.AuthRequestDto;
import com.company.SafarSaathi.user_service.dtos.UserDto;
import com.company.SafarSaathi.user_service.entities.User;
import com.company.SafarSaathi.user_service.enums.Role;
import com.company.SafarSaathi.user_service.exceptions.BadRequestException;
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
    private final JwtTokenProvider jwtTokenProvider;

    public UserDto signUp(AuthRequestDto authRequestDto){
        // check if the user already exists
        boolean exists = userRepository.existsByEmail(authRequestDto.getEmail());
        if(exists){
            throw new BadRequestException("User already exists, cannot signup again");
        }
        User user = modelMapper.map(authRequestDto,User.class);

        // check if the adminCode is provided and valid
        if("ADMIN123".equals(authRequestDto.getAdminCode())){
            user.setRole(Role.ADMIN);
        }else if(authRequestDto.getRole()==null){
            user.setRole(Role.TRAVELLER);
        }else {
            user.setRole(authRequestDto.getRole());
        }

        //hash the password before saving in the database
        user.setPassword(PasswordUtils.hashPassword(authRequestDto.getPassword()));

        // save the user in the database
        User savedUser = userRepository.save(user);

        //map saved User Entity to UserDto for response
        return modelMapper.map(savedUser, UserDto.class);
    }

}
