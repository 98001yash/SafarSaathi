package com.company.SafarSaathi.companion_service.dtos;


import com.company.SafarSaathi.companion_service.enums.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanionRequestResponseDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long tripId;
    private RequestStatus status;

    private LocalDateTime timeStamp;
}
