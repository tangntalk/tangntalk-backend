package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.security.jwt.JwtUtil;
import com.example.tangntalk.util.login.service.LoginService;
import com.example.tangntalk.web.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public Response.Item<String> login(
            @RequestBody AccountDto.Request.Login loginRequest,
            HttpServletResponse httpServletResponse
    ){

        AccountDto.Response.Login loginResponse = loginService.login(loginRequest);
        Cookie cookie = new Cookie(JwtUtil.JWT_COOKIE_KEY, loginResponse.getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return new Response.Item<>("정상적으로 로그인되었습니다.");

    }

    @PostMapping("/logout")
    public Response.Empty logout(HttpServletResponse httpServletResponse){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        loginService.updateConnectionFalse(username);
        return new Response.Empty();

    }
}
