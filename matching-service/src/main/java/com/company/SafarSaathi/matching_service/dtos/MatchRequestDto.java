package com.company.SafarSaathi.matching_service.dtos;


import lombok.Data;

@Data
public class MatchRequestDto {

    private Long userId;
    private Long tripId;
    private String status;
}
