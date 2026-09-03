package com.pankaj.linkedinProject.postService.service;

import com.pankaj.linkedinProject.postService.entity.Post;
import com.pankaj.linkedinProject.postService.entity.PostLike;
import com.pankaj.linkedinProject.postService.exception.BadRequestException;
import com.pankaj.linkedinProject.postService.exception.ResourceNotFoundException;
import com.pankaj.linkedinProject.postService.repository.PostLikeRepository;
import com.pankaj.linkedinProject.postService.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId= 1L;
        log.info("User with Id: {} Liking post with id {}",userId, postId);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLiked) throw new BadRequestException("Cannot Like the Post Again");
        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);

        //TODO: Send Notification to owner of this Post
    }

    @Transactional
    public void unlikePost(Long postId) {

        Long userId= 1L;
        log.info("User with Id: {} UnLikeing post with id {}",userId, postId);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!hasAlreadyLiked) throw new BadRequestException("Cannot UnLike the Post Again");
        postLikeRepository.deleteByUserIdAndPostId(userId,postId);
    }
}
