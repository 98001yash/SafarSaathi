package com.company.SafarSaathi.companion_service.dtos;


import lombok.Data;

@Data
public class CompanionRequestDto {


    private Long senderId;
    private Long receiverId;
    private Long tripId;
}
