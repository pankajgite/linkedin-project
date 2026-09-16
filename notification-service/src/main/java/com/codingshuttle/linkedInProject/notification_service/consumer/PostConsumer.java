package com.codingshuttle.linkedInProject.notification_service.consumer;

import com.codingshuttle.linkedInProject.notification_service.entity.Notification;
import com.codingshuttle.linkedInProject.notification_service.service.NotificationService;
import com.codingshuttle.linkedInProject.postsService.event.PostCreated;
import com.codingshuttle.linkedInProject.postsService.event.PostLiked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "post_created_topic")
    public void handlePostCreated(PostCreated postCreated){
        log.info("Received Post Created notification: {}", postCreated);

        String message = String.format("Your Connection with id: %d has created this post: %s",
                postCreated.getOwnerUserId(),
                postCreated.getContent());

        Notification notification = Notification.builder()
                .message(message)
                .userId(postCreated.getUserId())
                .build();
        notificationService.addNotification(notification);

    }

    @KafkaListener(topics = "post_liked_topic")
    public void  handlePostLiked(PostLiked postLiked){
        log.info("Received Post Liked notification: {}", postLiked);
        String message = String.format(
                "User with id: %s has liked your post with id: %s",
                postLiked.getLikedByUserId(),
                postLiked.getPostId()
        );

        Notification notification = Notification.builder()
                .message(message)
                .userId(postLiked.getOwnerUserId())
                .build();
        notificationService.addNotification(notification);
    }
}
