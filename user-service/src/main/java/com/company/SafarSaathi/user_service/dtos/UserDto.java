package com.company.SafarSaathi.user_service.dtos;


import com.company.SafarSaathi.user_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Role role = Role.TRAVELLER;
}
