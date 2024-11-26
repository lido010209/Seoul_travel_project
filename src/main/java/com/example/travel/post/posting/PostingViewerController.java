package com.example.travel.post.posting;

import com.example.travel.post.posting.dto.PostingDto;
import com.example.travel.post.posting.service.PostingService;
import com.example.travel.post.posting.service.PostingViewerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("posting-view")
public class PostingViewerController {
    private final PostingViewerService postingService;
    @GetMapping
    public List<PostingDto> allPosting(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "userId", required = false) Long userId
    ){
        if (keyword!=null) return postingService.allPostingKeyword(keyword);
        else if (userId!=null) return postingService.allPostingByWriter(userId);
        return postingService.allPosting();
    }


    @GetMapping("{postId}")
    public PostingDto readOne(@PathVariable("postId") Long postId){
        return postingService.readOne(postId);
    }

}
