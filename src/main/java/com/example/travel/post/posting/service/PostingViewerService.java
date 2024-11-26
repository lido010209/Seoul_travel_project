package com.example.travel.post.posting.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.post.comment.entity.Comment;
import com.example.travel.post.comment.repo.CommentRepo;
import com.example.travel.post.emotion.Like;
import com.example.travel.post.emotion.LikeRepo;
import com.example.travel.post.posting.dto.PostingDto;
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
public class PostingViewerService {
    private final PostRepo postRepo;
    private final ImagePostRepo imageRepository;
    private final CommentRepo commentRepo;
    private final LikeRepo likeRepo;
    private final UserComponent userComponent;

    public PostingDto readOne(Long postId){
        Posting posting = postRepo.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("No exist this posting!!!"));
        List<Comment> comments = commentRepo.listCommentPosting(posting);
        posting.setNumOfComment(comments.size());
        List<Like> likes = likeRepo.listLikePosting(posting);
        posting.setNumOfLike(likes.size());
        postRepo.save(posting);
        return PostingDto.fromEntity(posting);
    }

    public List<PostingDto> allPosting(){
        List<PostingDto> dtoList = new ArrayList<>();

        for (Posting posting: postRepo.findAll()){
            dtoList.add(PostingDto.fromEntity(posting));
        }
        return dtoList;
    }

    public List<PostingDto> allPostingKeyword(String keyword){
        List<PostingDto> dtoList = new ArrayList<>();
        for (Posting posting: postRepo.searchPostings(keyword)){
            dtoList.add(PostingDto.fromEntity(posting));
        }
        return dtoList;
    }

    public List<PostingDto> allPostingByWriter(Long userId){
        UserEntity writer = userComponent.userById(userId);
        List<PostingDto> dtoList = new ArrayList<>();
        for (Posting posting: postRepo.searchPostingsByWriter(writer)){
            dtoList.add(PostingDto.fromEntity(posting));
        }
        return dtoList;
    }
}
