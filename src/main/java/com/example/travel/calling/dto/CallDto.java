package com.example.travel.calling.dto;

import com.example.travel.calling.entity.Call;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallDto {
    private Long id;
    private String status;
    private String callerName;
    private String callerImg;
    private Long callerId;
    private String calleeName;
    private String calleeImg;
    private Long calleeId;
//    private boolean video;
    public static CallDto dto (Call call){
        CallDto callDto= CallDto.builder()
                .id(call.getId())
                .callerId(call.getCaller().getId())
                .callerName(call.getCaller().getName())
                .callerImg(call.getCaller().getProfileImg())
                .calleeId(call.getCallee().getId())
                .calleeName(call.getCallee().getName())
                .calleeImg(call.getCallee().getProfileImg())
                .status(call.getStatus().toString())
                .build();
        return callDto;
    }
}
