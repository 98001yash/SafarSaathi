package com.company.SafarSaathi.user_service.dtos;


import lombok.Data;

@Data
public class SignupRequestDto {

    private String name;
    private String email;
    private String password;

}
