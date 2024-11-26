package com.example.travel.post.posting.entity;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posting extends BaseEntity {
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity writer;
    @OneToMany(mappedBy = "posting")
    private final List<ImagePosting> images  =  new ArrayList<>();
    private Integer numOfLike;
    private Integer numOfComment;
}
