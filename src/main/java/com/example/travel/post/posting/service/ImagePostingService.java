package com.example.travel.post.posting.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.post.posting.dto.ImagePostingDto;
import com.example.travel.post.posting.entity.ImagePosting;
import com.example.travel.post.posting.entity.Posting;
import com.example.travel.post.posting.repo.ImagePostRepo;
import com.example.travel.post.posting.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImagePostingService {
    private final PostRepo postRepo;
    private final ImagePostRepo imagePostRepo;
//    private final static String[] images = {
//            "/static/visual/posting/image1.png",
//            "/static/visual/posting/image2.png",
//            "/static/visual/posting/image3.png",
//            "/static/visual/posting/image4.png"
//    };

    public ImagePostingService(PostRepo postRepo, ImagePostRepo imagePostRepo) {
        this.postRepo = postRepo;
        this.imagePostRepo = imagePostRepo;
//        for (String image: images){
//            if (!imagePostRepo.existsByLink(image)){
//                imagePostRepo.save(ImagePosting.builder().link(image).build());
//            }
//        }
    }

    //add Image to post
    public ImagePostingDto uploadImgs (Long postId, MultipartFile image) {
        Posting posting = postRepo.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("No exist posting!!!")
        );

        //2.파일의 업로드 위치를 결정한다
        //추천: posting_media/{postingId}
        String profileDir = "posting-media/" + postId + "/";
        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //2.2. 업로드한 파일의 확장자를 추출한다
        String originalFileName = image.getOriginalFilename();
        String[] fileNameSplit = originalFileName.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        //2.3.실제 위치에 파일을 저장한다
        //posting_media/1/image1.png
        ImagePosting newImg = ImagePosting.builder()
                .posting(posting)
                .build();
        imagePostRepo.save(newImg);
        String uploadPath = profileDir + String.format("image%s.%s", newImg.getId(), extension);
        try {
            image.transferTo(Path.of(uploadPath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //3.업로드에 성공하면, 이미지 URL을 Entity에 저장한다
        //http://localhost:8080/static/{postingId}/image1.png|jpeg

        String reqPath = String.format("/static/%s/image%s.%s", postId, newImg.getId(), extension);
        newImg.setLink(reqPath);

        return ImagePostingDto.fromEntity(imagePostRepo.save(newImg));
    }

    public void deleteImg(Long imageId){
        imagePostRepo.deleteById(imageId);
    }

    public List<ImagePostingDto> listImgOnePost(Long postId){
        List<ImagePostingDto> list = new ArrayList<>();
        Posting posting = postRepo.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("No exist posting!!!")
        );
        for (ImagePosting image: imagePostRepo.listImgsOfPost(posting)){
            list.add(ImagePostingDto.fromEntity(image));
        }
        return list;
    }
}
