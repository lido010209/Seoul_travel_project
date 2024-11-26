package com.example.travel.post.comment.repo;

import com.example.travel.post.comment.entity.Comment;
import com.example.travel.post.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    @Query("SELECT DISTINCT c FROM Comment c JOIN FETCH c.posting WHERE c.posting=:posting")
    List<Comment> listCommentPosting(@Param("posting")Posting posting);
}
