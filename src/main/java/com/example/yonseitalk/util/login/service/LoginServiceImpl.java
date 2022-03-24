package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.security.jwt.JwtUtil;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
    private final AccountService accountService;
    private final JwtUtil jwtUtil;

    public AccountDto.Response.Login login(AccountDto.Request.Login loginRequest){
        String loginId = loginRequest.getAccountId();
        String password = loginRequest.getPassword();
        AccountDto accountDto = accountService.findById(loginId).orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디입니다"));

        System.out.println(password);
        System.out.println(accountDto.getPassword());
        if(!password.equals(accountDto.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다");
        }
        updateConnectionTrue(loginId);

        String token = jwtUtil.issueToken(loginId);

        return new AccountDto.Response.Login(loginId,token);
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
