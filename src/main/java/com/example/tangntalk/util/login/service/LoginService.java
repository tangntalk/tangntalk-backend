package com.example.tangntalk.util.login.service;

import com.example.tangntalk.security.jwt.JwtUtil;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService{
    private final AccountService accountService;
    private final JwtUtil jwtUtil;


    public AccountDto.Response.Login login(AccountDto.Request.Login loginRequest){
        String loginId = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        AccountDto accountDto = accountService.findByUsername(loginId).orElseThrow(()-> new IllegalArgumentException("가입되지 않은 아이디입니다"));


        System.out.println(password);
        System.out.println(accountDto.getPassword());
//        if(!password.equals(account.getPassword())){
//            throw new IllegalArgumentException("잘못된 비밀번호입니다");
//        }
        updateConnectionTrue(loginId);

        String token = jwtUtil.issueToken(loginId, accountDto.getRole());

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
