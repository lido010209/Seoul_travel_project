package com.example.travel.tourPackage.dto;

import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.entity.Schedule;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PackageDto {
    private Long id; //form "contentId,contentId,..."
    private Integer price;
    private Double avgRating;
    private Integer numOfRating;
    private String title;
    private final List<String> landmarks = new ArrayList<>();
    private final List<String> mainActivities = new ArrayList<>();
    private final List<String> details = new ArrayList<>();
    public static PackageDto dto(Package entity){
        PackageDto packageDto = PackageDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .avgRating(entity.getAvgRating())
                .numOfRating(entity.getNumOfRating())
                .title(entity.getTitle()).build();
        for (Schedule schedule: entity.getSchedules()){
            packageDto.mainActivities.add(schedule.getMainActivity());
            packageDto.details.add(schedule.getDetail());
        }
        for (String landmark: entity.getListTourists().split(",")){
            packageDto.landmarks.add(landmark);
        }
        return packageDto;
    }
}
