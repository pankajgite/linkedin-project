package com.pankaj.linkedinProject.postService.repository;

import com.pankaj.linkedinProject.postService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
