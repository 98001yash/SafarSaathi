package com.company.SafarSaathi.auth_service.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicProfileCreateRequest {
    private Long userId;
    private String fullName;
    private String email;
}
