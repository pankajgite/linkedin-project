package com.codingshuttle.linkedInProject.notification_service.consumer;

import com.codingshuttle.linkedInProject.ConnectionsService.event.UserAcceptConnectionEvent;
import com.codingshuttle.linkedInProject.ConnectionsService.event.UserRequestedEvent;
import com.codingshuttle.linkedInProject.notification_service.entity.Notification;
import com.codingshuttle.linkedInProject.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "request_topic")
    public void consumeConnectionRequest(UserRequestedEvent userRequestedEvent) {
        log.info("Received connection request event {}", userRequestedEvent);
        String message = String.format("Received connection request from userId: %s . %s", userRequestedEvent.getFromUserId(),userRequestedEvent.getMessage());
        Notification notification= Notification.builder()
                .message(message)
                .userId(userRequestedEvent.getToUserId())
                .build();
        notificationService.addNotification(notification);

    }

    @KafkaListener(topics = "connection_accepted_topic")
    public void consumeConnectionRequest(UserAcceptConnectionEvent userAcceptConnectionEvent) {
        log.info("{} Accepted your Connection Request", userAcceptConnectionEvent.getActionUserId());
        String message = String.format("%s Accepted you Connection Request",userAcceptConnectionEvent.getActionUserId());
        Notification notification = Notification.builder()
                .message(message)
                .userId(userAcceptConnectionEvent.getOnActionUserId())
                .build();
        notificationService.addNotification(notification);

    }
}
