package com.example.travel.message;

import com.example.travel.message.dto.MessageDto;
import com.example.travel.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages/admin")
public class MesAdminController {
    private final MessageService messageService;

    @GetMapping("chat/{receiverId}")
    public List<MessageDto> chatbotAdmin(
            @PathVariable("receiverId")
            Long receiverId
    ){
        return messageService.chatbot(receiverId);
    }
    @PutMapping("confirm/{messageId}")
    public MessageDto confirm(@PathVariable("messageId") Long messageId){
        return messageService.confirm(messageId);
    }
    @GetMapping("count-user")
    public Integer countUserSendMes(){
        return messageService.countUserSendMes();
    }
    @GetMapping("count-mes/{userId}")
    public Integer countMesByUser(@PathVariable("userId") Long senderId){
        return messageService.countMesByUser(senderId);
    }
}
