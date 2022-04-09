package com.example.tangntalk.web.account.dto.response;

import com.example.tangntalk.web.account.domain.Account;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class FriendSearchDto {
    private String username;
    private String name;
    private String statusMessage;
    private String type;
    private Boolean isFriend;

    public static FriendSearchDto fromAccount(Account account , boolean isFriend){
        return FriendSearchDto.builder()
                .username(account.getUsername())
                .name(account.getName())
                .statusMessage(account.getStatusMessage())
                .type(account.getAccountType())
                .isFriend(isFriend).build();
    }
}