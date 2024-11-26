package com.example.travel.tourPackage.entity;

import com.example.travel.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Package extends BaseEntity {
    private String listTourists; //form "contentId,contentId,..." "2733967,1116925"
    private Integer price; //
    private Double avgRating;
    private Integer numOfRating;
    private String title; //
    @OneToMany(mappedBy = "tourPackage")
    private final List<Schedule> schedules = new ArrayList<>();
}
