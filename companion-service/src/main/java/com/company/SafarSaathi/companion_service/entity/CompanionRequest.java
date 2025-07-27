package com.company.SafarSaathi.companion_service.entity;


import com.company.SafarSaathi.companion_service.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name  ="companion_requests")
public class CompanionRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long receiverId;

    private Long tripId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime timeStamp;
}
