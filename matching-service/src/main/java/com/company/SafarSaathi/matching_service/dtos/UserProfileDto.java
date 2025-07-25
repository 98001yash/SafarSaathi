package com.company.SafarSaathi.matching_service.dtos;


import lombok.Data;

@Data
public class UserProfileDto {

    private Long id;
    private String name;
    private Integer age;

    private String gender;

    private Boolean smoker;
    private Boolean drinker;
}
