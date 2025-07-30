package com.company.SafarSaathi.matching_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private Long id;
    private String name;
    private Integer age;

    private String gender;

    private Boolean smoker;
    private Boolean drinker;
}
