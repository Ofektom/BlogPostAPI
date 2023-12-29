package com.ofektom.springsecclass.repository;

import com.ofektom.springsecclass.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    Comments getCommentById(Long id);
}
