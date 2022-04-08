package com.example.tangntalk.web.account.dto;

import com.example.tangntalk.web.account.domain.Account;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class FriendSearchResponse{
    private String username;
    private String name;
    private String statusMessage;
    private String type;
    private Boolean isFriend;

    public static FriendSearchResponse fromAccount(Account account ,boolean isFriend){
        return FriendSearchResponse.builder()
                .username(account.getUsername())
                .name(account.getName())
                .statusMessage(account.getStatusMessage())
                .type(account.getAccountType())
                .isFriend(isFriend).build();
    }
}