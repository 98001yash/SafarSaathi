package com.company.SafarSaathi.matching_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PythonMatchResponse {


    private CompanionProfile profile;
    private double score;
}
