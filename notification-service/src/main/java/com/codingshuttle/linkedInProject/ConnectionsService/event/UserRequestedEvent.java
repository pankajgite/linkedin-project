package com.codingshuttle.linkedInProject.ConnectionsService.event;

import lombok.Builder;
import lombok.Data;

@Data
public class UserRequestedEvent {
    private Long toUserId;
    private Long fromUserId;
    private String message;
}
