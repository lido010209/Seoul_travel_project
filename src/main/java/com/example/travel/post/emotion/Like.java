package com.example.travel.post.emotion;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import com.example.travel.post.posting.entity.Posting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "likes")
public class Like extends BaseEntity {
    @Column(name = "is_liked")
    private boolean like;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private Posting posting;
}
