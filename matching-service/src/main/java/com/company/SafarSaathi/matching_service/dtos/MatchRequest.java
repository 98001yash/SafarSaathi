package com.company.SafarSaathi.matching_service.dtos;


import lombok.Data;

import java.util.List;

@Data
public class MatchRequest {

private CompanionProfile userProfile;
private List<CompanionProfile> candidates;
}
