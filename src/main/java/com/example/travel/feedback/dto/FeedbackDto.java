package com.example.travel.feedback.dto;

import com.example.travel.feedback.entity.Feedback;
import com.example.travel.tourPackage.dto.PackageDto;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FeedbackDto {
    private Long id;
    private String content;
    private Integer rating;
    private String customerName;
    private Long packageId;
    public static FeedbackDto dto(Feedback feedback){
        FeedbackDto feedbackDto = FeedbackDto.builder()
                .id(feedback.getId())
                .content(feedback.getContent())
                .customerName(feedback.getCustomer().getName())
                .packageId(PackageDto.dto(feedback.getTourPackage()).getId())
                .build();
        return feedbackDto;
    }
}
