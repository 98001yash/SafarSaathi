package com.company.SafarSaathi.auth_service.dtos;


import lombok.Data;

@Data
public class SignupRequestDto {

    private String name;
    private String email;
    private String password;

    private Integer age;
    private String gender;
    private Boolean smoker;
    private Boolean drinker;

}
