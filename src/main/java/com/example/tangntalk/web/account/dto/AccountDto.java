package com.example.tangntalk.web.account.dto;

import com.example.tangntalk.security.authorization.role.Role;
import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.dto.projection.NearByAccountProjection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class AccountDto {

    private String username;
    private String name;
    private String email;
    private String password;
    private String role;
    private String statusMessage;
    private String type;
    private String accountLocation;
    private Boolean connectionStatus;

    public static AccountDto fromAccount(Account user){
        return AccountDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .statusMessage(user.getStatusMessage())
                .type(user.getAccountType())
                .accountLocation(user.getAccountLocation())
                .connectionStatus(user.getConnectionStatus())
                .build();
    }

    public Account toAccount(){
        return Account.builder()
                .username(username)
                .name(name)
                .email(email)
                .password(password)
                .role(Role.valueOf(role))
                .statusMessage(statusMessage)
                .accountType(type)
                .accountLocation(accountLocation)
                .connectionStatus(connectionStatus)
                .build();
    }
}
