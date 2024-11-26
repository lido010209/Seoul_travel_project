package com.example.travel.post.comment;

import com.example.travel.post.comment.dto.CommentDto;
import com.example.travel.post.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {
     private final CommentService commentService;

    @PostMapping
    public CommentDto create(
            @RequestBody
            CommentDto dto
    ) {
        return commentService.create(dto);
    }

    @PutMapping("{commentId}")
    public CommentDto update(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto
    ){
        return commentService.update(dto, commentId);
    }

    @DeleteMapping("{commentId}")
    public void delete(@PathVariable("commentId") Long commentId){
        commentService.delete(commentId);
    }

    @GetMapping("list/{postId}")
    public List<CommentDto> list(
            @PathVariable("postId") Long postId
    ) {
        return commentService.listCommentPost(postId);
    }

    @GetMapping("{commentId}")
    public CommentDto readOne(@PathVariable("commentId") Long commentId) {
        return commentService.readOne(commentId);
    }
}

