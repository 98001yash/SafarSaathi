package com.company.SafarSaathi.user_service.dtos;


import lombok.Data;

@Data
public class LoginRequestDto {

    private String email;
    private String password;
}
