package com.example.travel.post.posting.dto;

import com.example.travel.post.posting.entity.ImagePosting;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class ImagePostingDto {
    private Long id;
    private String link;
    private Long postingId;

    public static ImagePostingDto fromEntity(ImagePosting entity) {
        ImagePostingDto dto = ImagePostingDto.builder()
                .id(entity.getId())
                .link(entity.getLink())
                .postingId(entity.getPosting().getId())
                .build();
        return dto;
    }
}
