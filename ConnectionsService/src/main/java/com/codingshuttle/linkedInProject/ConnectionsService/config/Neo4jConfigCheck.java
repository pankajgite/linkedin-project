package com.codingshuttle.linkedInProject.ConnectionsService.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Neo4jConfigCheck {

    @Value("${spring.neo4j.database:NOT_FOUND}")
    private String database;

    @Value("${spring.neo4j.uri:NOT_FOUND}")
    private String uri;

    @PostConstruct
    public void printConfig() {
        System.out.println("================================");
        System.out.println("Neo4j URI      : " + uri);
        System.out.println("Neo4j Database : " + database);
        System.out.println("================================");
    }
}