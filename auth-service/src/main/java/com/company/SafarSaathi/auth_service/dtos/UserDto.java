package com.company.SafarSaathi.auth_service.dtos;


import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;

    private Integer age;
    private String gender;
    private Boolean smoker;
    private Boolean drinker;

}
