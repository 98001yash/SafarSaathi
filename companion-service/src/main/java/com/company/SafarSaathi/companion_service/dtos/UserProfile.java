package com.company.SafarSaathi.companion_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    private Long id;
    private int age;
    private String gender;
    private boolean smoker;
    private boolean drinker;
}
