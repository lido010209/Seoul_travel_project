package com.example.travel.post.posting.entity;

import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImagePosting extends BaseEntity {
    private String link;
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;
}
