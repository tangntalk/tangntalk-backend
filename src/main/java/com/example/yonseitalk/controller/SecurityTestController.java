package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.user.dao.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/test")
@RestController
public class SecurityTestController {

    @GetMapping("/permit-all")
    public User getTestPermitAll(){
        User testUser = User.builder().name("test-permit-all").build();
        return testUser;
    }
}
