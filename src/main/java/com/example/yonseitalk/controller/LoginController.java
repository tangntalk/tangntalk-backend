package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.util.login.service.LoginService;
import com.example.yonseitalk.util.login.service.LoginServiceImpl;
import com.example.yonseitalk.util.login.LoginFormat;
import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@CrossOrigin("*")
@RequestMapping("")
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

//    @GetMapping("/")
//    @ResponseBody
//    public void loginEnter(HttpServletRequest request, HttpServletResponse response) throws IOException{
//        // 로그인 들어오는것
//        //프론트에서 알아서 해준다.
//        //기존에 로그인이 되어 있으면 바로 해당 유저의 공간으로 리디렉션해줌
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Object user = session.getAttribute("loginUser");
//            Account loginUser= (Account)user;
//            String redirect="/"+loginUser.getAccountId();
//            response.sendRedirect(redirect);
//        }
//    }
    @PostMapping("/login")
    public Response.Item<AccountDto.Response.Login> login(@RequestBody AccountDto.Request.Login loginRequest){

        return new Response.Item<>(loginService.login(loginRequest));

    }

    @PostMapping("/{account_id}/logout")
    public Response.Empty logout(@PathVariable("account_id") String accountId){

        loginService.updateConnectionFalse(accountId);
        return new Response.Empty();

    }

}
