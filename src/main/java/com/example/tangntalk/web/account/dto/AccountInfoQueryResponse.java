package com.example.tangntalk.web.account.dto;

import com.example.tangntalk.web.account.domain.Account;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class AccountInfoQueryResponse {
    String name;
    String statusMessage;
    String locationName;

    public static AccountInfoQueryResponse fromAccount(Account account){
        return AccountInfoQueryResponse.builder()
                .name(account.getName())
                .statusMessage(account.getStatusMessage())
                .locationName(account.getAccountLocation())
                .build();
    }

}
