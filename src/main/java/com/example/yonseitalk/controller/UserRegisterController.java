package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.dto.UserRegisterRequest;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/register")
public class UserRegisterController {
    private final UserService userService;

    @GetMapping("")
    public void enterRegister(){
        //프론트에서 알아서 함.
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public UserDto register(@RequestBody UserRegisterRequest userRegisterRequest){
        return userService.save(userRegisterRequest);
    }

}
