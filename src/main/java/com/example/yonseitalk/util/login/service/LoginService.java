package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AccountService accountService;

    public AccountDto login(String loginId, String password){

        return accountService.findById(loginId).filter(m -> m.getPassword().equals(password)).orElse(null);
//        return userService.findById(loginId).get();
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
