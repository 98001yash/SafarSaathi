package com.company.SafarSaathi.auth_service.dtos;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String fullName;
    private String email;
    private String password;
}
