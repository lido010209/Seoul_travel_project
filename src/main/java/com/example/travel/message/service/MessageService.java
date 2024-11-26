package com.example.travel.message.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.message.dto.MessageDto;
import com.example.travel.message.entity.Message;
import com.example.travel.message.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;
    private final UserComponent userComponent;

    public List<MessageDto> chatbot(Long receiverId){
        List<MessageDto> list = new ArrayList<>();
        UserEntity sender = userComponent.userLogin();
        UserEntity receiver = userComponent.userById(receiverId);

        List<Message> messages = messageRepo.chatMessages(sender, receiver);
        for (Message message: messages){
            list.add(MessageDto.dto(message));
        }
        return list;
    }

    public MessageDto confirm(Long messageId){
        UserEntity receiver= userComponent.userLogin();
        Message message = messageRepo.findById(messageId)
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getReceiver().getId().equals(receiver.getId()))
            throw new IllegalArgumentException("No exist message");
        message.setConfirm(true);
        return MessageDto.dto(messageRepo.save(message));
    }
    public Integer countUserSendMes(){
        UserEntity user = userComponent.userLogin();
        return messageRepo.countUserSendMes(user);
    }

    public Integer countMesByUser(Long senderId){
        UserEntity user = userComponent.userLogin();
        UserEntity sender = userComponent.userById(senderId);
        return messageRepo.countMes(user, sender);
    }
}
