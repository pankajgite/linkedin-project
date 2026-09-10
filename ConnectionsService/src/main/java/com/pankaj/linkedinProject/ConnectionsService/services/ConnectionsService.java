package com.pankaj.linkedinProject.ConnectionsService.services;

import com.pankaj.linkedinProject.ConnectionsService.entity.Person;
import com.pankaj.linkedinProject.ConnectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        log.info("geting First degree connections of user with id {}",userId);
        return personRepository.getFirstDegreeConnections(userId);
    }


}
