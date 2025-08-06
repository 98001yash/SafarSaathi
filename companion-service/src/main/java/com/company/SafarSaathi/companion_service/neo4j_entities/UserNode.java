package com.company.SafarSaathi.companion_service.neo4j_entities;


import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;


@Node("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNode {
    @Id
    private Long userId;

    @Relationship(type = "SENT_REQUEST", direction = Relationship.Direction.OUTGOING)
    private List<CompanionRequestRelation> sentRequests;
}
