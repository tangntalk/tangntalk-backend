package com.example.tangntalk.util.login.service;

import com.example.tangntalk.security.jwt.JwtUtil;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService{

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    public AccountDto.Response.Login login(AccountDto.Request.Login loginRequest){

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        AccountDto accountDto = accountService.findByUsername(username).orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디입니다"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//        System.out.println(password);
//        System.out.println(accountDto.getPassword());
//        if(!password.equals(account.getPassword())){
//            throw new IllegalArgumentException("잘못된 비밀번호입니다");
//        }
        updateConnectionTrue(username);

        String token = jwtUtil.issueToken(username, accountDto.getRole());

        return new AccountDto.Response.Login(username,token);
    }

    @Transactional
    public void updateConnectionTrue(String Id){
        accountService.updateAccountConnectionStatus(Id,true);
    }

    @Transactional
    public void updateConnectionFalse(String Id){
        accountService.updateAccountConnectionStatus(Id,false);
    }

}
