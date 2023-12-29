package com.ofektom.springsecclass.repository;

import com.ofektom.springsecclass.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}