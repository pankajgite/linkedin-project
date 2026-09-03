package com.pankaj.linkedinProject.postService.repository;

import com.pankaj.linkedinProject.postService.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {


    boolean existsByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
