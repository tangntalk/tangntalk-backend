package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.domain.Account;
import lombok.Builder;
import lombok.Data;

@Data
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

    public AccountInfoQueryResponse toAccount(){
        return AccountInfoQueryResponse.builder()
                .name(name)
                .statusMessage(statusMessage)
                .locationName(locationName)
                .build();
    }
}
