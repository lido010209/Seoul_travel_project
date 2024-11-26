package com.example.travel.authentication.user.entity;

import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Authority extends BaseEntity {
    private String role;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private final Set<UserEntity> users = new HashSet<>();
}
