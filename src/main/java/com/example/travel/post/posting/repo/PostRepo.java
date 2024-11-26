package com.example.travel.post.posting.repo;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.post.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Posting,Long> {
    @Query("SELECT DISTINCT p FROM Posting p " +
            "WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Posting> searchPostings(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p FROM Posting p JOIN FETCH p.writer WHERE p.writer = :user")
    List<Posting> searchPostingsByWriter(@Param("user") UserEntity writer);
}
