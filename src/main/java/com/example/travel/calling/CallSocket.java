package com.example.travel.calling;

import com.example.travel.calling.dto.CallDto;
import com.example.travel.calling.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CallSocket {
    private final CallService callService;
    @MessageMapping("/signaling")
    @SendTo("/topic/signaling")
    public CallDto startCall(CallDto dto){
        return callService.startCall(dto);
    }

    @MessageMapping("/on-call")
    @SendTo("/topic/on-call")
    public CallDto onCall(CallDto dto){
        return callService.receiveCall(dto.getId());
    }

    @MessageMapping("/reject")
    @SendTo("/topic/reject")
    public CallDto reject(CallDto dto){
        return callService.rejectCall(dto.getId());
    }
    @MessageMapping("/miss-call")
    @SendTo("/topic/miss-call")
    public CallDto missCall(CallDto dto){
        return callService.missCall(dto.getId());
    }

    @MessageMapping("/hangup")
    @SendTo("/topic/hangup")
    public CallDto hangup(CallDto dto){
        return callService.hangup(dto.getId());
    }

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String createOffer(@Payload String offer){
        return offer;
    }

    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public String createAnswer(@Payload String answer){
        return answer;
    }
    @MessageMapping("/candidate")
    @SendTo("/topic/candidate")
    public String candidate(@Payload String candidate){
        return candidate;
    }
}
