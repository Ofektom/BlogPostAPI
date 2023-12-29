package com.ofektom.springsecclass.controller;

import com.ofektom.springsecclass.model.Like;
import com.ofektom.springsecclass.model.Users;
//import com.ofektom.springsecclass.serviceImpl.CommentServiceImpl;
import com.ofektom.springsecclass.serviceImpl.LikeServiceImpl;
import com.ofektom.springsecclass.serviceImpl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeServiceImpl likeService;
    private final PostServiceImpl postService;
//    private  final CommentServiceImpl commentService;

    @Autowired
    public LikeController(LikeServiceImpl likeService, PostServiceImpl postService) {
        this.likeService = likeService;
        this.postService = postService;
//        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<Like> likePost(
            @PathVariable Long postId,
            @PathVariable Long userId) {
        Like savedLike = likeService.likePost(postId, userId);
        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }

    @PostMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<Like> likeComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {
        Like savedLike = likeService.likeComment(commentId, userId);
        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }


    private Users getCurrentUser() {
        return new Users();
    }
}
