package com.example.travel.tourPackage.entity;

import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule extends BaseEntity {
    private String day;
    private String mainActivity;
    private String detail;
    @ManyToOne
    private Package tourPackage;
}
