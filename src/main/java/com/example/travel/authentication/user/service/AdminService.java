package com.example.travel.authentication.user.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.dto.UserDto;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.authentication.user.repo.UserRepo;
import com.example.travel.message.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;
    private final UserComponent userComponent;
    private final MessageRepo messageRepo;

    public UserDto admin(Long senderId){
        UserEntity user = userComponent.userLogin();
        UserEntity sender = userComponent.userById(senderId);
        user.setCountMesByUser(messageRepo.countMes(user, sender));
        user.setCountUserSendMes(messageRepo.countUserSendMes(sender));
        return UserDto.dto(userRepo.save(user));
    }
}
