package com.company.SafarSaathi.Bot_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotQueryRequest {

    private String message;
    private Long tripId;

}
