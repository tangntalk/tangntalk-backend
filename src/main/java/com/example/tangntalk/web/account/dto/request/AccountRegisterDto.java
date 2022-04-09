package com.example.tangntalk.web.account.dto.request;

import com.example.tangntalk.web.account.domain.Account;
import lombok.Data;

@Data
public class AccountRegisterDto {
    private String username;
    private String name;
    private String password;
    private String type;

}
