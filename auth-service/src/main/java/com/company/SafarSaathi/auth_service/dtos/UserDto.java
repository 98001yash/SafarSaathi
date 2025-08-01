package com.company.SafarSaathi.auth_service.dtos;

import com.company.SafarSaathi.auth_service.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
