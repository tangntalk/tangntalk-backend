package com.example.tangntalk.web.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccessDto {
    public String username;
    public String token;
}
