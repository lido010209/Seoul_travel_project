package com.example.travel.post.emotion;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PostMapping("{postId}")
    public LikeDto like(@PathVariable("postId") Long postId){
        return likeService.create(postId);
    }

    @GetMapping("{postId}")
    public boolean checkLike(@PathVariable("postId") Long postId){
        return likeService.existLike(postId);
    }

    @DeleteMapping("{postId}")
    public void delete(@PathVariable("postId") Long postId){
        likeService.delete(postId);
    }
}
