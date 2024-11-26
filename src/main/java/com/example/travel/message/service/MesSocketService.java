package com.example.travel.message.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.message.dto.MessageDto;
import com.example.travel.message.entity.Message;
import com.example.travel.message.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MesSocketService {
    private final UserComponent userComponent;
    private final MessageRepo messageRepo;

    public MessageDto create(MessageDto dto){
        UserEntity sender = userComponent.userById(dto.getSenderId());
        UserEntity receiver = userComponent.userById(dto.getReceiverId());

        Message newMessage = Message.builder()
                .content(dto.getContent())
                .sender(sender)
                .receiver(receiver)
                .edit(false)
                .confirm(false)
                .build();
        return MessageDto.dto(messageRepo.save(newMessage));
    }

    public MessageDto edit(MessageDto dto){
        UserEntity sender = userComponent.userById(dto.getSenderId());
        Message message = messageRepo.findById(dto.getId())
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getSender().getId().equals(sender.getId()))
            throw new IllegalArgumentException("No exist message");
        message.setContent(dto.getContent());
        message.setEdit(true);
        return MessageDto.dto(messageRepo.save(message));
    }

    public Long delete(MessageDto dto){
        UserEntity sender = userComponent.userById(dto.getSenderId());
        Message message = messageRepo.findById(dto.getId())
                .orElseThrow(()-> new IllegalArgumentException("No exist message!!"));
        if (!message.getSender().getId().equals(sender.getId()))
            throw new IllegalArgumentException("No exist message");
        messageRepo.delete(message);
        return dto.getId();
    }
}
