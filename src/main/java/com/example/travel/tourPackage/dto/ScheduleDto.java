package com.example.travel.tourPackage.dto;

import com.example.travel.tourPackage.entity.Schedule;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScheduleDto {
    private Long id;
    private String day;
    private String mainActivity;
    private String detail;
    private Long packageId;
    public static ScheduleDto dto(Schedule schedule){
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .id(schedule.getId())
                .day(schedule.getDay())
                .mainActivity(schedule.getMainActivity())
                .detail(schedule.getDetail())
                .packageId(schedule.getTourPackage().getId())
                .build();
        return scheduleDto;
    }
}
