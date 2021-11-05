package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.LoginService;
import com.example.yonseitalk.domain.LoginFormat;
import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.repository.DBUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/login")
    @ResponseBody
    public void loginEnter(){
        // 로그인 들어오는것
        //프론트에서 알아서 해준다.
    }
    @PostMapping("/login")
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("loginFormat:{}",messageBody);
        LoginFormat loginFormat=objectMapper.readValue(messageBody,LoginFormat.class);


        User loginUser=loginService.login(loginFormat.getUser_id(),loginFormat.getPassword());

        if(loginUser==null){
            response.sendError(401);
            return;
        }
        log.info("loginFormat:{}",loginFormat);
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginUser", loginUser);

        String redirect="/"+loginFormat.getUser_id();
        response.sendRedirect(redirect);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        String redirect="/login";
        response.sendRedirect(redirect);

    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
