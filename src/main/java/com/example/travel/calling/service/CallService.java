package com.example.travel.calling.service;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.calling.dto.CallDto;
import com.example.travel.calling.entity.Call;
import com.example.travel.calling.repo.CallRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CallService {
    private final CallRepo callRepo;
    private final UserComponent userComponent;
    public boolean checkAvailable(Long calleeId){
        UserEntity callee = userComponent.userById(calleeId);
        if (callRepo.checkConnect(callee).isEmpty()){
//            throw new IllegalArgumentException("On another connection!!!");
            return true;
        }
        return false;
    }
    public CallDto startCall(CallDto dto){
        UserEntity caller = userComponent.userById(dto.getCallerId());
        UserEntity callee = userComponent.userById(dto.getCalleeId());
//        if (callRepo.checkConnect(callee).isPresent()){
//            throw new IllegalArgumentException("On another connection!!!");
//        }
        Call newCall = Call.builder()
                .callee(callee)
                .caller(caller)
                .status(Call.Status.Connecting)
//                .video(dto.isVideo())
                .build();
        return CallDto.dto(callRepo.save(newCall));
    }

    public CallDto receiveCall(Long callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new IllegalArgumentException("No exist this calling!!!")
        );
        call.setStatus(Call.Status.OnCall);
        return CallDto.dto(callRepo.save(call));
    }

    public CallDto rejectCall(Long callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new IllegalArgumentException("No exist this calling!!!")
        );
        call.setStatus(Call.Status.Reject);
        return CallDto.dto(callRepo.save(call));
    }

    @Transactional
    public CallDto missCall(Long callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new IllegalArgumentException("No exist this calling!!!")
        );
        call.setStatus(Call.Status.MissCall);
        return CallDto.dto(callRepo.save(call));
    }

    public CallDto hangup(Long callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new IllegalArgumentException("No exist this calling!!!")
        );
        call.setStatus(Call.Status.Hangup);
        return CallDto.dto(callRepo.save(call));
    }

    public void cancelCall(Long callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new IllegalArgumentException("No exist this calling!!!")
        );
        callRepo.delete(call);
    }
    public CallDto getCall(Long callId){
        Call call = callRepo.findById(callId).orElseThrow(
                ()-> new IllegalArgumentException("No exist this calling!!!")
        );
        return CallDto.dto(call);
    }
    public CallDto markMissCall(){
//        Call call= callRepo.findByCalleeAndCallerAndStatus(
//                userComponent.userLogin(), userComponent.userById(1L), Call.Status.MissCall
//        ).orElseThrow(
//                ()-> new IllegalArgumentException("No exist connection!!!")
//        );
        Optional<Call> call= callRepo.findByCalleeAndCallerAndStatus(
                userComponent.userLogin(), userComponent.userById(1L), Call.Status.MissCall
        );
        if (call.isEmpty()) return CallDto.builder().id(null).build();
        return CallDto.dto(call.get());
    }
    public CallDto markAdmin(Long callerId){
//        Call call= callRepo.findByCalleeAndCallerAndStatus(
//                userComponent.userLogin(), userComponent.userById(callerId), Call.Status.MissCall
//        ).orElseThrow(
//                ()-> new IllegalArgumentException("No exist connection!!!")
//        );
        Optional<Call> call= callRepo.findByCalleeAndCallerAndStatus(
                userComponent.userLogin(), userComponent.userById(callerId), Call.Status.MissCall
        );
        if (call.isEmpty()) return CallDto.builder().id(null).build();
        return CallDto.dto(call.get());
    }
    public boolean mark(){
        return callRepo.existsByCalleeAndStatus(userComponent.userLogin(), Call.Status.MissCall);
    }
}
