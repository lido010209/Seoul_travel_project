package com.example.travel.feedback.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.feedback.dto.FeedbackDto;
import com.example.travel.feedback.entity.Feedback;
import com.example.travel.feedback.repo.FeedbackRepo;
import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.repo.PackageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepo feedbackRepo;
    private final UserComponent userComponent;
    private final PackageRepo packageRepo;

    public FeedbackDto create(FeedbackDto dto){
        UserEntity userLogin = userComponent.userLogin();
        Package pack = packageRepo.findById(dto.getPackageId())
                .orElseThrow(()-> new IllegalArgumentException("No exist tour package!!!"));
        Feedback feedback = Feedback.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .tourPackage(pack)
                .customer(userLogin)
                .build();
        return FeedbackDto.dto(feedbackRepo.save(feedback));
    }

    public FeedbackDto edit(FeedbackDto dto){
        UserEntity userLogin = userComponent.userLogin();
        Package pack = packageRepo.findById(dto.getPackageId())
                .orElseThrow(()-> new IllegalArgumentException("No exist tour package!!!"));
        Feedback feedback = feedbackRepo
                .findByCustomerAndTourPackage(userLogin, pack)
                .orElseThrow(()-> new IllegalArgumentException("No exist feedback!!!"));
        feedback.setContent(dto.getContent());
        feedback.setRating(dto.getRating());
        return FeedbackDto.dto(feedbackRepo.save(feedback));
    }

    public List<FeedbackDto> feedbackPackage(Long packageId){
        List<FeedbackDto> dtoList = new ArrayList<>();
        Package pack = packageRepo.findById(packageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist tour package!!!"));
        List<Feedback> feedbacks = feedbackRepo.findByTourPackage(pack);
        for (Feedback feedback: feedbacks){
            dtoList.add(FeedbackDto.dto(feedback));
        }
        return dtoList;
    }
}
