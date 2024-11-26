package com.example.travel.feedback;

import com.example.travel.feedback.dto.FeedbackDto;
import com.example.travel.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @PostMapping
    public FeedbackDto create(@RequestBody FeedbackDto dto){
        return feedbackService.create(dto);
    }
    @PutMapping
    public FeedbackDto update(@RequestBody FeedbackDto dto){
        return feedbackService.edit(dto);
    }

    @GetMapping("{packageId}")
    public List<FeedbackDto> feedbackList(@PathVariable("packageId") Long packageId){
        return feedbackService.feedbackPackage(packageId);
    }

}
