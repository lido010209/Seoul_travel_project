package com.example.travel.post.posting.repo;

import com.example.travel.post.posting.entity.ImagePosting;
import com.example.travel.post.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagePostRepo extends JpaRepository<ImagePosting, Long> {
    @Query("SELECT DISTINCT i FROM ImagePosting i JOIN FETCH i.posting WHERE i.posting = :posting")
    List<ImagePosting> listImgsOfPost(@Param("posting")Posting posting);
    boolean existsByLink(String link);
    Optional<ImagePosting> findByLink (String link);
}
