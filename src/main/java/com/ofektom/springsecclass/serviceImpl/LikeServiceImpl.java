package com.ofektom.springsecclass.serviceImpl;

//import com.ofektom.springsecclass.model.Comment;
import com.ofektom.springsecclass.model.Comments;
import com.ofektom.springsecclass.model.Like;
import com.ofektom.springsecclass.model.Post;
import com.ofektom.springsecclass.model.Users;
import com.ofektom.springsecclass.repository.LikeRepository;
import com.ofektom.springsecclass.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LikeServiceImpl{

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;

    @Autowired
    public LikeServiceImpl(
            LikeRepository likeRepository,
            PostRepository postRepository,
            CommentServiceImpl commentService,
            UserServiceImpl userService) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.userService = userService;
    }

    public Like likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found with ID: " + postId));
        Users user = userService.findUserById(userId);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .createdAt(new Date())
                .build();
        return likeRepository.save(like);
    }

    public Like likeComment(Long commentId, Long userId) {
        Comments comment = commentService.getCommentById(commentId);
        Users user = userService.findUserById(userId);
        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .createdAt(new Date())
                .build();
        return likeRepository.save(like);
    }
}

