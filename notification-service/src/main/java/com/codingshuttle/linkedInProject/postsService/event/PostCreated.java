package com.codingshuttle.linkedInProject.postsService.event;

import lombok.Builder;
import lombok.Data;

@Data
public class PostCreated {
    private Long postId;
    private Long ownerUserId;
    private Long userId;
    private String content;

}
