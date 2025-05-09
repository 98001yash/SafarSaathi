package com.company.SafarSaathi.user_service.dtos;


import com.company.SafarSaathi.user_service.enums.Role;
import lombok.Data;

@Data
public class AuthRequestDto {

    private String name;
    private String email;
    private String password;
    private Role role;
    private String adminCode;
}
