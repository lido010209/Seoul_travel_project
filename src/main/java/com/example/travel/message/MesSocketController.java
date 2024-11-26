package com.example.travel.message;

import com.example.travel.message.dto.MessageDto;
import com.example.travel.message.entity.Message;
import com.example.travel.message.service.MesSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MesSocketController {
    private final MesSocketService mesSocketService;

    @MessageMapping("/new-mes")
    @SendTo("/topic/display-mes")
    public MessageDto newMessage(MessageDto dto) {
        return mesSocketService.create(dto);
    }

    @MessageMapping("/edit")
    @SendTo("/topic/edit")
    public MessageDto editMessage(MessageDto dto) {
        return mesSocketService.edit(dto);
    }

    @MessageMapping("/remove")
    @SendTo("/topic/remove")
    public Long removeMessage(MessageDto dto) {
        return mesSocketService.delete(dto);
    }
}
