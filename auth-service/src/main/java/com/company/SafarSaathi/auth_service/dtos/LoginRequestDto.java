package com.company.SafarSaathi.auth_service.dtos;


import lombok.Data;

@Data
public class LoginRequestDto {

    private String email;
    private String password;
}
