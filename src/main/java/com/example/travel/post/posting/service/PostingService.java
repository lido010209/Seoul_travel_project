package com.example.travel.post.posting.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.post.posting.dto.PostingDto;
import com.example.travel.post.posting.entity.ImagePosting;
import com.example.travel.post.posting.entity.Posting;
import com.example.travel.post.posting.repo.ImagePostRepo;
import com.example.travel.post.posting.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostingService {
    private final PostRepo postRepo;
    private final ImagePostRepo imageRepo;
    private final UserComponent userComponent;


    //create
    public PostingDto create(PostingDto dto) {
        UserEntity writer = userComponent.userLogin();
        Posting posting = Posting.builder()
                .title(dto.getTitle())
                .writer(writer)
                .content(dto.getContent())
                .build();
        return PostingDto.fromEntity(postRepo.save(posting));
    }

    //update post
    public PostingDto update(Long postId, PostingDto dto) {
        UserEntity writer = userComponent.userLogin();
        Posting posting = postRepo.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("No exist posting!")
        );
        if (!posting.getWriter().getId().equals(writer.getId()))
            throw new IllegalArgumentException("This posting is not available!!!");
        posting.setTitle(dto.getTitle());
        posting.setContent(dto.getContent());
        return PostingDto.fromEntity(postRepo.save(posting));
    }
    //delete post
    public void delete(Long postId) {
        UserEntity writer = userComponent.userLogin();
        Posting posting = postRepo.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("No exist posting!")
        );
        if (!posting.getWriter().getId().equals(writer.getId()))
            throw new IllegalArgumentException("This posting is not available!!!");
        postRepo.delete(posting);
    }

    public List<PostingDto> allPostingByWriter(){
        UserEntity writer = userComponent.userLogin();
        List<PostingDto> dtoList = new ArrayList<>();
        for (Posting posting: postRepo.searchPostingsByWriter(writer)){
            dtoList.add(PostingDto.fromEntity(posting));
            log.info(posting.getImages().toString());
        }
        return dtoList;
    }
}
