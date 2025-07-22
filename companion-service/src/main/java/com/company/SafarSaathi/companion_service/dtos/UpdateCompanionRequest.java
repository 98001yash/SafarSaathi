package com.company.SafarSaathi.companion_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCompanionRequest {
    private Long tripId;
    private String status;
    private String message;
}