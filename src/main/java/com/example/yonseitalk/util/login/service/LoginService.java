package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.web.account.dto.AccountDto;

public interface LoginService {

    public AccountDto.Response.Login login(AccountDto.Request.Login loginRequest);

    public void updateConnectionTrue(String Id);
    public void updateConnectionFalse(String Id);
}
