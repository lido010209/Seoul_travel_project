package com.example.travel.calling.entity;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "calling")
public class Call extends BaseEntity {
    @ManyToOne
    private UserEntity caller;
    @ManyToOne
    private UserEntity callee;
//    private boolean video;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status {
        Connecting, OnCall, Reject, MissCall, Hangup
    }
}
