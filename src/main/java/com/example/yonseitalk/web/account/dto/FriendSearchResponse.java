package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.domain.Account;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class FriendSearchResponse{
    private String accountId;
    private String name;
    private String statusMessage;
    private String type;
    private Boolean isFriend;

    public static FriendSearchResponse fromAccount(Account account ,boolean isFriend){
        return FriendSearchResponse.builder()
                .accountId(account.getAccountId())
                .name(account.getName())
                .statusMessage(account.getStatusMessage())
                .type(account.getType())
                .isFriend(isFriend).build();
    }
}