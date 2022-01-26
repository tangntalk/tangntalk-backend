package com.example.yonseitalk.controller;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.domain.Role;
import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.domain.user.UserRegisterRequest;
import com.example.yonseitalk.repository.*;
import com.example.yonseitalk.service.UserService;
import com.example.yonseitalk.view.DefaultResponse;
import com.example.yonseitalk.view.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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
    public ResponseEntity<DefaultResponse> register(@RequestBody UserRegisterRequest userRegisterRequest){

        User user = userService.save(userRegisterRequest);
        return ResponseEntity.status(201).body(
                new DefaultResponse(true)
        );

    }

}
