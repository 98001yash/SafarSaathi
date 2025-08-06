package com.company.SafarSaathi.companion_service.neo4j_entities;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("User")
public class UserNode {

    @Id
    private Long id;

    private String name;

    @Relationship(type = "SENT_REQUEST_TO")
    private List<CompanionRequestRel> requests = new ArrayList<>();
}
