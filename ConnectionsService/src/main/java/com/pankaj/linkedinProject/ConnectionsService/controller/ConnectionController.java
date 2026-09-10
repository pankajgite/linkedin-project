package com.pankaj.linkedinProject.ConnectionsService.controller;

import com.pankaj.linkedinProject.ConnectionsService.entity.Person;
import com.pankaj.linkedinProject.ConnectionsService.services.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ConnectionController {
    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId) {
        List<Person> porsonList = connectionsService.getFirstDegreeConnectionsOfUser(userId);
        return ResponseEntity.ok(porsonList);
    }
}
