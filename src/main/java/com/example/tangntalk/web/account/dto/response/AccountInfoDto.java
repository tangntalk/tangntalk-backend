package com.example.tangntalk.web.account.dto.response;

import com.example.tangntalk.web.account.domain.Account;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class AccountInfoDto {
    String name;
    String statusMessage;
    String locationName;

    public static AccountInfoDto fromAccount(Account account){
        return AccountInfoDto.builder()
                .name(account.getName())
                .statusMessage(account.getStatusMessage())
                .locationName(account.getAccountLocation())
                .build();
    }

}
