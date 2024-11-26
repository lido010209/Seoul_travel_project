package com.example.travel.authentication.user.entity;

import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String profileImg;
    private Integer countUserSendMes;
    private Integer countMesByUser;

    @ManyToMany(fetch = FetchType.LAZY)
    private final Set<Authority> authorities = new HashSet<>();
}
