package com.example.travel.post.emotion;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class LikeDto {
    private Long id;
    private boolean like;
    private Long postingId;

    public static LikeDto dto (Like like){
        LikeDto likeDto = LikeDto.builder()
                .id(like.getId())
                .like(like.isLike())
                .postingId(like.getPosting().getId())
                .build();
        return likeDto;
    }
}
