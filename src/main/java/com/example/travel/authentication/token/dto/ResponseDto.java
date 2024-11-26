package com.example.travel.authentication.token.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ResponseDto {
    private String token;
//    private Long adminId;
}
