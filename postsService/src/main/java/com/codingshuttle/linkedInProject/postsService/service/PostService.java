package com.codingshuttle.linkedInProject.postsService.service;

import com.codingshuttle.linkedInProject.postsService.auth.AuthContextHolder;
import com.codingshuttle.linkedInProject.postsService.client.ConnectionServiceClient;
import com.codingshuttle.linkedInProject.postsService.dto.PersonDto;
import com.codingshuttle.linkedInProject.postsService.dto.PostCreateRequestDto;
import com.codingshuttle.linkedInProject.postsService.dto.PostDto;
import com.codingshuttle.linkedInProject.postsService.entity.Post;
import com.codingshuttle.linkedInProject.postsService.exception.ResourceNotFoundException;
import com.codingshuttle.linkedInProject.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionServiceClient connectionServiceClient;


    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Creating post for user with id: {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("Getting the post with ID: {} for User Id: {}", postId,userId);

//        TODO : Remove in future
//        Call the connection service from post service and pass the user id inside the header
        List<PersonDto> personDtoList = connectionServiceClient.getFirstDegreeConnections(userId);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found " +
                "with ID: "+postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all the posts of a user with ID: {}", userId);
        List<Post> postList = postRepository.findByUserId(userId);

        return postList
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }
}
