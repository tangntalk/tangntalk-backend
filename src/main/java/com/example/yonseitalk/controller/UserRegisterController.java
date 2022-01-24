package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserRegisterRequest;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("")
    public void register(@RequestBody UserRegisterRequest userRegisterRequest , HttpServletResponse response) throws IOException {

        if(userService.findById(userRegisterRequest.getUserId()).isEmpty()){
            User user = userService.save(userRegisterRequest);
            log.info("User={}", user);
            //true로 응답을 줘라.(이건 일단 이대로 리다이렉팅으로 하자)
            response.setStatus(201);
        }
        else{
            response.setStatus(401);
        }

    }

}
