package com.example.travel.authentication.component;

import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.authentication.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserComponent {
    private final UserRepo userRepo;

    public UserEntity userLogin(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity entity = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist"));
        UserEntity user = userRepo.userWithAuthority(entity.getId()).orElseThrow();
        return user;
    }

    public UserEntity userById(Long id){
        UserEntity entity = userRepo.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Username does not exist"));
        UserEntity user = userRepo.userWithAuthority(entity.getId()).orElseThrow();
        return user;
    }
}
