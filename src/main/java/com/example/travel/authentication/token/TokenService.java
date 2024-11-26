package com.example.travel.authentication.token;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.token.dto.RequestDto;
import com.example.travel.authentication.token.dto.ResponseDto;
import com.example.travel.authentication.user.dto.UserDto;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final UserComponent userComponent;

    public ResponseDto getToken(RequestDto dto){
        UserDto user = (UserDto) userService.loadUserByUsername(dto.getUsername());
        if (!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Wrong password!!!");
        }
        ResponseDto responseDto = ResponseDto.builder()
                .token(tokenUtils.generateToken(user))
//                .adminId(user.getId())
                .build();
        return responseDto;
    }

    public ResponseDto tokenOAuth2(){
        UserEntity user = userComponent.userLogin();
        UserDto userDto = UserDto.dto(user);
        String token = tokenUtils.generateToken(userDto);
        ResponseDto responseDto = ResponseDto.builder()
                .token(token).build();
        return responseDto;
    }
}
