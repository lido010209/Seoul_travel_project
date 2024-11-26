package com.example.travel.post.comment.entity;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import com.example.travel.post.posting.entity.Posting;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String content;
    @ManyToOne
    private UserEntity writer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Posting posting;
}
