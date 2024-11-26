package com.example.travel.authentication.user;

import com.example.travel.authentication.component.UserComponent;
import com.example.travel.authentication.user.dto.UserDto;
import com.example.travel.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final UserComponent userComponent;
    @PostMapping
    public UserDto create(@RequestBody UserDto dto){
        return userService.create(dto);
    }

    @GetMapping
    public UserDto user(){
        return userService.userLogin();
    }

    @PutMapping
    public UserDto updateInfo(@RequestBody UserDto dto){
        return userService.changeInfo(dto);
    }

    @PutMapping("password")
    public UserDto updateOtherInfo(
            @RequestParam String oldPw,
            @RequestParam String newPw
    ){
        return userService.changePassword(oldPw, newPw);
    }

    @PutMapping("avatar")
    public UserDto updateImg(
            @RequestParam("avatar")
            MultipartFile avatar
    ){
        return userService.changeAvatar(avatar);
    }
//    @GetMapping("mes/{id}")
//    public Integer mesCount(@PathVariable("id") Long friendId){
//        return afterLoginService.mesCount(friendId);
//    }

    @GetMapping("all")
    public List<UserDto> allUser(){
        return userService.allUser();
    }
    @GetMapping("{userId}")
    public UserDto user(@PathVariable("userId") Long userId){
        return UserDto.dto(userComponent.userById(userId));
    }


}
