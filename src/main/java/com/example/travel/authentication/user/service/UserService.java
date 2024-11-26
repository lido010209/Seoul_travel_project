package com.example.travel.authentication.user.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.dto.UserDto;
import com.example.travel.authentication.user.entity.Authority;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.authentication.user.repo.AuthorityRepo;
import com.example.travel.authentication.user.repo.UserRepo;
import com.example.travel.message.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthorityRepo authorityRepo;
    private final UserComponent userComponent;
    private final MessageRepo messageRepo;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity entity = userRepo.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("Username does not exists!!!")
        );
        UserEntity user = userRepo.userWithAuthority(entity.getId()).orElseThrow();
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto create(UserDto dto){
        if (userRepo.existsByUsername(dto.getUsername())) throw new IllegalArgumentException("Username already exists!!!");
        else if(userRepo.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("Email already exists!!!");
        else if(userRepo.existsByPhone(dto.getPhone())) throw new IllegalArgumentException("Phone already exists!!!");

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .name(dto.getName())
                .profileImg("/static/visual/user.png")
                .build();
        Authority authority = authorityRepo.findByRole("ROLE_USER").orElseThrow();
        user.getAuthorities().add(authority);
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto changeInfo(UserDto dto){
        UserEntity user = userComponent.userLogin();
        if (userRepo.existsByEmail(dto.getEmail()) && !user.getEmail().equals(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists!!!");
        if (userRepo.existsByPhone(dto.getPhone()) && !user.getPhone().equals(dto.getPhone()))
            throw new IllegalArgumentException("Phone number already exists!!!");

        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setName(dto.getName());
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto changePassword(String oldPassword, String newPassword){
        UserEntity user = userComponent.userLogin();

        if (!encoder.matches(oldPassword, user.getPassword()))
            throw new IllegalArgumentException("Password is wrong!!!");
        user.setPassword(encoder.encode(newPassword));
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto changeAvatar(MultipartFile image){
        UserEntity user = userComponent.userLogin();
        String directory="profile/"+user.getId()+"/";
        try{
            Files.createDirectories(Path.of(directory));
        } catch (IOException exception){
            throw new RuntimeException("Fail to upload file");
        }
        String fileName= image.getOriginalFilename();
        String[] eleFile= fileName.split("\\.");
        String extension = eleFile[eleFile.length-1];

        String path = directory+ "profile."+extension;
        try{
            image.transferTo(Path.of(path));
        } catch (IOException exception){
            throw new RuntimeException("Fail to upload file");
        }

        String url = "/static/"+user.getId()+"/profile."+extension;
        user.setProfileImg(url);
        return UserDto.dto(userRepo.save(user));
    }

    public UserDto userLogin(){
        return UserDto.dto(userComponent.userLogin());
    }

    public List<UserDto> allUser(){
        List<UserDto> users = new ArrayList<>();
        for (UserEntity user: userRepo.findAll()){
            if (!user.getId().equals(userComponent.userLogin().getId())){
                users.add(UserDto.dto(user));
            }
        }
        return users;
    }


}
