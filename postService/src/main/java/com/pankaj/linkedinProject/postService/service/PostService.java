package com.pankaj.linkedinProject.postService.service;

import com.pankaj.linkedinProject.postService.dto.PostCreateRequestDto;
import com.pankaj.linkedinProject.postService.dto.PostDto;
import com.pankaj.linkedinProject.postService.entity.Post;
import com.pankaj.linkedinProject.postService.exception.ResourceNotFoundException;
import com.pankaj.linkedinProject.postService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("Createing post for user with id {}",userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        postRepository.save(post);
        return modelMapper.map(post,PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        log.info("Getting post with id {}",postId);
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not Found with Id:"+postId));
        return modelMapper.map(post,PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all posts of user with  Id {}", userId);
        List<Post> postList=postRepository.findByUserId(userId);
        return postList
                .stream()
                .map((element)-> modelMapper.map(element,PostDto.class))
                .collect(Collectors.toList());

    }
}
