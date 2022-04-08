package com.example.tangntalk.web.account.dto;

import com.example.tangntalk.web.account.domain.Account;
import lombok.Data;

@Data
public class AccountRegisterRequest {
    private String username;
    private String name;
    private String password;
    private String type;

}
