package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin("*")
@RequestMapping("/register")
public class UserRegisterController {

    @Autowired
    private DBUserRepository userRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    public void enterRegister(){
        //프론트에서 알아서 함.
    }

    @PostMapping("")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        User user = objectMapper.readValue(messageBody, User.class);
        // 여기에 추가적으로 디폴트 값들 채워 넣기
        user.setConnection_status(false);
        user.setStatus_message("");
        user.setUser_location("공학관");

        log.info("User={}",user);


        Boolean isPresentUser= userRepository.findById(user.getUser_id()).isEmpty();

        if(isPresentUser){
            log.info("User={}", user);
            userRepository.save(user);
            //true로 응답을 줘라.(이건 일단 이대로 리다이렉팅으로 하자)
            response.setStatus(201);

        }
        else{
            // status로 보내주기 401로 보내고 API명세에 반영해라.
            response.sendError(401);
        }

    }
}
