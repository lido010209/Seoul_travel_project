package com.example.travel.post.comment.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.post.comment.dto.CommentDto;
import com.example.travel.post.comment.entity.Comment;
import com.example.travel.post.comment.repo.CommentRepo;
import com.example.travel.post.posting.entity.Posting;
import com.example.travel.post.posting.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepo commentRepo;
    private final UserComponent userComponent;
    private final PostRepo postRepo;

    //create
    public CommentDto create(CommentDto dto) {
        UserEntity user = userComponent.userLogin();
        Posting posting = postRepo.findById(dto.getPostingId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No exist posting!!!")
        );

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(user)
                .posting(posting)
                .build();
        return CommentDto.fromEntity(commentRepo.save(comment));
    }

    //edit comment
    public CommentDto update(CommentDto dto, Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("No exist comment"));
        UserEntity writer = userComponent.userLogin();
        if (!comment.getWriter().getId().equals(writer.getId()))
            throw new IllegalArgumentException("This comment is not available!!!");
        comment.setContent(dto.getContent());
        return CommentDto.fromEntity(commentRepo.save(comment));
    }

    //delete
    public void delete(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("No exist comment"));
        UserEntity writer = userComponent.userLogin();
        if (!comment.getWriter().getId().equals(writer.getId()))
            throw new IllegalArgumentException("This comment is not available!!!");
        commentRepo.delete(comment);
    }

    public CommentDto readOne(Long commentId) {
        Comment comment=commentRepo.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("No exist comment!!!"));
        return CommentDto.fromEntity(comment);
    }

    //read list comment
    public List<CommentDto> listCommentPost(Long postId) {
        List<CommentDto> dtos= new ArrayList<>();
        Posting posting = postRepo.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("No exist posting"));
        for (Comment comment: commentRepo.listCommentPosting(posting)){
            dtos.add(CommentDto.fromEntity(comment));
        }
        return dtos;
    }
}
