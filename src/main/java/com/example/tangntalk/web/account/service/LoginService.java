package com.example.tangntalk.web.account.service;

import com.example.tangntalk.security.jwt.JwtUtil;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.dto.request.LoginDto;
import com.example.tangntalk.web.account.dto.response.LoginSuccessDto;
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

    public LoginSuccessDto login(LoginDto loginRequest){

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        AccountDto accountDto = accountService.findByUsername(username).orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디입니다"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // TODO: 없이도 정상작동해야함.
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        updateConnectionTrue(username);

        String token = jwtUtil.issueToken(username, accountDto.getRole());

        return new LoginSuccessDto(username,token);
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
