package com.ofektom.springsecclass.serviceImpl;

import com.ofektom.springsecclass.dto.EditPostRequestDto;
import com.ofektom.springsecclass.dto.PostRequestDto;
import com.ofektom.springsecclass.enums.Role;
import com.ofektom.springsecclass.model.Post;
import com.ofektom.springsecclass.model.Users;
import com.ofektom.springsecclass.repository.PostRepository;
import com.ofektom.springsecclass.repository.UserRepository;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class PostServiceImpl {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Post> savePost(Post post, Users currentUser) {
        Users user = userRepository.findById(currentUser.getId()).orElseThrow(()-> new RuntimeException("User not found: " + currentUser));

            post.setUser(user);
            postRepository.save(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }

    public ResponseEntity<List<Post>> getAllPost() {
        List<Post> postList = postRepository.findAll();
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }
    public ResponseEntity<Post> findPostById(Long postId) {
        return new ResponseEntity<>(postRepository.findById(postId).orElseThrow(()-> new RuntimeException("No Post Found with ID" + postId)), HttpStatus.FOUND);
    }


    public ResponseEntity<Post> editPostById(Long postId, Post newPost) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found"));
        post.setTitle(newPost.getTitle());
        post.setDescription(newPost.getDescription());
        postRepository.save(post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    public void deletePostById(Long postId, Users currntUser) {
        currntUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
        postRepository.deleteById(postId);
    }

    public ResponseEntity<String> createPost(PostRequestDto postRequestDto, Long userId) {
        Users optionalUser = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No user with ID " + userId + " found in my database"));


        Post post = new Post();
        post.setDescription(postRequestDto.getContent());
        post.setTitle(postRequestDto.getPostTitle());
        post.setUser(optionalUser);
        Post post1 = this.postRepository.save(post);
        return new ResponseEntity<>(post1.getTitle(), HttpStatus.CONFLICT);

    }

    public ResponseEntity<String> editPostContent(EditPostRequestDto editPostRequestDto, Long userId, Long postId) {
        Post optionalPost = this.postRepository.findById(postId).orElseThrow(() -> new RuntimeException("No post with ID " + postId + " found in my database"));
        Users optionalUser = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No user with ID " + userId + " found in my database"));


        if (!optionalPost.getUser().equals(optionalUser)){
            throw new RuntimeException("Please you can not edit this post simply because you were not the one that made the posted ");
        }

        optionalPost.setDescription(editPostRequestDto.getContent());
        this.postRepository.save(optionalPost);

        return new ResponseEntity<>("Post successfully editted", HttpStatus.OK  );
    }
}

