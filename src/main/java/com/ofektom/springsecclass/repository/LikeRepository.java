package com.ofektom.springsecclass.repository;

//import com.ofektom.springsecclass.model.Comment;
import com.ofektom.springsecclass.model.Like;
import com.ofektom.springsecclass.model.Post;
import com.ofektom.springsecclass.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPostAndUser(Post post, Users user);

//    Optional<Like> findByCommentAndUser(Comment comment, Users user);

    // Add custom query methods if needed
}
