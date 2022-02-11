package com.example.yonseitalk.controller;

import com.example.yonseitalk.util.login.service.LoginService;
import com.example.yonseitalk.util.login.LoginFormat;
import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.util.login.jwt.JwtUtil;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Controller
@CrossOrigin("*")
@RequestMapping
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    @ResponseBody
    public void loginEnter(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 로그인 들어오는것
        //프론트에서 알아서 해준다.
        //기존에 로그인이 되어 있으면 바로 해당 유저의 공간으로 리디렉션해줌
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("loginUser");
            Account loginUser= (Account)user;
            String redirect="/"+loginUser.getAccountId();
            response.sendRedirect(redirect);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) throws IOException {

        Map<String, Object> response = new HashMap<>();

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("loginFormat:{}",messageBody);
        LoginFormat loginFormat=objectMapper.readValue(messageBody,LoginFormat.class);


        AccountDto loginUser = loginService.login(loginFormat.getUser_id(),loginFormat.getPassword());

        if(loginUser==null){
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        log.info("loginFormat:{}",loginFormat);
        //status change True
        loginService.updateConnectionTrue(loginUser.getAccountId());

        String token="";
        token = jwtUtil.generateToken(loginUser);
        response.put("jwt",token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{user_id}/logout")
    public void logout(@PathVariable("user_id") String userId,HttpServletRequest request,HttpServletResponse response) throws IOException{


        loginService.updateConnectionFalse(userId);
        response.setStatus(200);

    }

}
