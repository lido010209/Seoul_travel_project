package com.example.travel.calling;

import com.example.travel.calling.dto.CallDto;
import com.example.travel.calling.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("call")
public class CallHttp {
    private final CallService callService;
    @DeleteMapping("{callId}")
    public void deleteCall(@PathVariable("callId") Long callId){
        callService.cancelCall(callId);
    }
    @GetMapping("{callId}")
    public CallDto getCall(@PathVariable("callId") Long callId){
        return callService.getCall(callId);
    }
    @PutMapping("{callId}")
    public CallDto missCall(@PathVariable("callId") Long callId){
        return callService.missCall(callId);
    }
    @GetMapping("check-available/{calleeId}")
    public boolean checkConnect(@PathVariable("calleeId") Long calleeId){
        return callService.checkAvailable(calleeId);
    }

    @GetMapping("miss-call")
    public CallDto markUser(){
        return callService.markMissCall();
    }
    @GetMapping("miss-call/admin/{callerId}")
    public CallDto markAdmin(@PathVariable("callerId") Long callerId){
        return callService.markAdmin(callerId);
    }
    @GetMapping("miss-call/admin")
    public boolean markAdmin(){
        return callService.mark();
    }
}
