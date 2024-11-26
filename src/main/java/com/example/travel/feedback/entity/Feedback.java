package com.example.travel.feedback.entity;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import com.example.travel.tourPackage.entity.Package;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback extends BaseEntity {
    private String content;
    private Integer rating;
    @ManyToOne
    private UserEntity customer;
    @ManyToOne
    private Package tourPackage;

}
