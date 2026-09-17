package com.codingshuttle.linkedInProject.ConnectionsService.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAcceptConnectionEvent {
    private Long actionUserId;
    private Long onActionUserId;
}
