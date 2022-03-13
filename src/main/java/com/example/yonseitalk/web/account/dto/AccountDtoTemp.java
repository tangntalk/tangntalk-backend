package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.domain.Account;
import lombok.*;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class AccountDtoTemp {
    private String accountId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String statusMessage;
    private String type;
    private String accountLocation;
    private Boolean connectionStatus;

    public static AccountDtoTemp fromAccount(Account user){
        return AccountDtoTemp.builder()
                .accountId(user.getAccountId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .statusMessage(user.getStatusMessage())
                .type(user.getType())
                .accountLocation(user.getAccountLocation())
                .connectionStatus(user.getConnectionStatus())
                .build();
    }

    public Account toAccount(){
        return Account.builder()
                .accountId(accountId)
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .statusMessage(statusMessage)
                .type(type)
                .accountLocation(accountLocation)
                .connectionStatus(connectionStatus)
                .build();
    }
}
