package com.example.travel.message;

import com.example.travel.message.dto.MessageDto;
import com.example.travel.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages/user")
public class MesUserController {
    private final MessageService messageService;

    @GetMapping("chat")
    public List<MessageDto> chatbot(){
        return messageService.chatbot(1L);
    }
    @PutMapping("confirm/{messageId}")
    public MessageDto confirm(@PathVariable("messageId") Long messageId){
        return messageService.confirm(messageId);
    }

    @GetMapping("count-mes")
    public Integer countMesByUser(){
        return messageService.countMesByUser(1L);
    }
}
