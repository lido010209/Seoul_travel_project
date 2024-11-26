package com.example.travel.post.comment.dto;

import com.example.travel.post.comment.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class CommentDto {
    private Long id;
    private String content;
    private String writer;
    private Long writerId;
    private String writerImg;
    private Long postingId;

    public static CommentDto fromEntity(Comment entity) {
        CommentDto dto = CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .writer(entity.getWriter().getName())
                .writerId(entity.getWriter().getId())
                .writerImg(entity.getWriter().getProfileImg())
                .postingId(entity.getPosting().getId())
                .build();
        return dto;
    }
}
