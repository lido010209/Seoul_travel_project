package com.example.travel.post.emotion;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.post.comment.entity.Comment;
import com.example.travel.post.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like, Long> {
    boolean existsByPostingAndUser(Posting posting, UserEntity user);
    @Query("SELECT DISTINCT l FROM Like l JOIN FETCH l.posting WHERE l.posting=:posting")
    List<Like> listLikePosting(@Param("posting")Posting posting);
    Optional<Like> findByPostingAndUser(Posting posting, UserEntity user);
}
