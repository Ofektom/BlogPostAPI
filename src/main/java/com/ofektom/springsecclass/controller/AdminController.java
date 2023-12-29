package com.ofektom.springsecclass.controller;

import com.ofektom.springsecclass.model.Users;
import com.ofektom.springsecclass.serviceImpl.PostServiceImpl;
import com.ofektom.springsecclass.serviceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    public AdminController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping("/ban-user/{userId}")
    public ResponseEntity<Void> banUser(@PathVariable Long userId) {
        userService.banUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{userId}")
    public ResponseEntity<Users> findUser(@PathVariable Long userId){
        Users user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePosts(@PathVariable Long postId, @AuthenticationPrincipal Users user) {
        postService.deletePostById(postId, user);
        return ResponseEntity.ok().build();
    }
}
