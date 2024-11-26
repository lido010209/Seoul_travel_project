package com.example.travel.message.entity;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends BaseEntity {
    private String content;
    private boolean edit;
    private boolean confirm;
    @ManyToOne
    private UserEntity sender;
    @ManyToOne
    private UserEntity receiver;
}
