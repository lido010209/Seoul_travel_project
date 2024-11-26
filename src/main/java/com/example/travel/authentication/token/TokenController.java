package com.example.travel.authentication.token;

import com.example.travel.authentication.token.dto.RequestDto;
import com.example.travel.authentication.token.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("issue")
    public ResponseDto getToken(@RequestBody RequestDto dto){
        return tokenService.getToken(dto);
    }

    @GetMapping("oauth")
    public ResponseDto validateTest(){
        return tokenService.tokenOAuth2();
    }
}
