package com.codingshuttle.linkedInProject.ConnectionsService.event;

import lombok.Builder;
import lombok.Data;

@Data
public class UserAcceptConnectionEvent {
    private Long actionUserId;
    private Long onActionUserId;
}
