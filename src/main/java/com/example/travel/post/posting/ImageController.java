package com.example.travel.post.posting;

import com.example.travel.post.posting.dto.ImagePostingDto;
import com.example.travel.post.posting.service.ImagePostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("post-images")
public class ImageController {
    private final ImagePostingService imageService;
    @PostMapping("{postId}")
    public ImagePostingDto uploadImgs(
            @PathVariable("postId") Long postId,
            @RequestParam("image") MultipartFile image
    ){
        return imageService.uploadImgs(postId, image);
    }

    @GetMapping("{postId}")
    public List<ImagePostingDto> listImgs(
            @PathVariable("postId") Long postId
    ){return imageService.listImgOnePost(postId);}
}
