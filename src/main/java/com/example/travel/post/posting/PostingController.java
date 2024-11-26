package com.example.travel.post.posting;

import com.example.travel.post.posting.dto.PostingDto;
import com.example.travel.post.posting.service.PostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("posting")
public class PostingController {
    private final PostingService postingService;

    @PostMapping
    public PostingDto create(
            @RequestBody PostingDto dto
    ) {
        return postingService.create(dto);
    }

    @PutMapping("{postId}")
    public PostingDto update(
            @PathVariable("postId") Long postId,
            @RequestBody PostingDto dto
    ) {return postingService.update(postId, dto);}

    @DeleteMapping("{id}")
    public void postDelete(
            @PathVariable("id") Long postId
    ) {postingService.delete(postId);}

//    @GetMapping
    @GetMapping
    public List<PostingDto> allPostingByWriter(){
        return postingService.allPostingByWriter();
    }
}
