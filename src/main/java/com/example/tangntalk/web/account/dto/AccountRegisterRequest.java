package com.example.tangntalk.web.account.dto;

import com.example.tangntalk.web.account.domain.Account;
import lombok.Data;

@Data
public class AccountRegisterRequest {
    private String accountId;
    private String name;
    private String password;
    private String type;

    public Account toEntity(){
        return Account.builder()
                .accountId(accountId)
                .name(name)
                .password(password)
                .type(type)
                .build();
    }
}