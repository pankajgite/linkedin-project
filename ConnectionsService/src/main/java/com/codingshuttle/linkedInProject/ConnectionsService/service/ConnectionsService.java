package com.codingshuttle.linkedInProject.ConnectionsService.service;

import com.codingshuttle.linkedInProject.ConnectionsService.auth.AuthContextHolder;
import com.codingshuttle.linkedInProject.ConnectionsService.entity.Person;
import com.codingshuttle.linkedInProject.ConnectionsService.event.UserAcceptConnectionEvent;
import com.codingshuttle.linkedInProject.ConnectionsService.event.UserRequestedEvent;
import com.codingshuttle.linkedInProject.ConnectionsService.repository.PersonRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, UserRequestedEvent>  kafkaTemplate;
    private final KafkaTemplate<Long, UserAcceptConnectionEvent>  kafkaTemplate1;


    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        log.info("Getting first degree connections of user with ID: {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

    public List<Person> getAllRequests() {
        Long userId = AuthContextHolder.getCurrentUserId();
        return personRepository.getAllRequest(userId);
    }

    public void sendConnectionRequest(Long receiverId, String message) {
        Long senderId = AuthContextHolder.getCurrentUserId();
        log.info("sending connection request with senderId: {}, receiverId: {}", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (alreadySentRequest) {
            throw new BadRequestException("Connection request already exists, cannot send again");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Already connected users, cannot add connection request");
        }

        personRepository.addConnectionRequest(senderId, receiverId);
        UserRequestedEvent userRequested = UserRequestedEvent
                .builder()
                .fromUserId(senderId)
                .toUserId(receiverId)
                .message(message)
                .build();
        kafkaTemplate.send("request_topic",userRequested);
        log.info("Successfully sent the connection request");
    }

    public void acceptConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Accepting a connection request with senderId: {}, receiverId: {}", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new BadRequestException("Already connected users, cannot accept connection request again");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new BadRequestException("No Connection request exists, cannot accept without request");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        UserAcceptConnectionEvent acceptedConnection = UserAcceptConnectionEvent
                .builder()
                .actionUserId(receiverId)
                .onActionUserId(senderId)
                .build();
        kafkaTemplate1.send("connection_accepted_topic",acceptedConnection);
        log.info("Successfully accepted the connection request with senderId: {}, receiverId: {}", senderId,
                receiverId);

    }

    public void rejectConnectionRequest(Long senderId) {
        Long receiverId = AuthContextHolder.getCurrentUserId();
        log.info("Rejecting a connection request with senderId: {}, receiverId: {}", senderId, receiverId);

        if (senderId.equals(receiverId)) {
            throw new BadRequestException("Both sender and receiver are the same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (!alreadySentRequest) {
            throw new BadRequestException("No Connection request exists, cannot reject it");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);

        log.info("Successfully rejected the connection request with senderId: {}, receiverId: {}", senderId,
                receiverId);
    }



}
