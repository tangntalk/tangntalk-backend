package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.domain.Account;
import lombok.Builder;
import lombok.Data;

public class AccountDtoMerged {
    public static class Request {

        @Data
        public static class Register {
            private String accountId;
            private String name;
            private String password;
            private String type;

            public Account toEntity(){
                return Account.builder()
                        .accountId(accountId)
                        .name(name)
                        .password(password)
                        .type(type)
                        .build();
            }
        }

        @Data
        public static class ModifyInfo {
            private String accountLocation;
            private String statusMessage;
        }


    }

    public static class Response {
        @Data
        @Builder
        public static class AccountInfo{
            String name;
            String statusMessage;
            String accountLocation;

            public static AccountInfo fromAccount(Account account){
                return AccountInfo.builder()
                        .name(account.getName())
                        .statusMessage(account.getStatusMessage())
                        .accountLocation(account.getAccountLocation())
                        .build();
            }

            public Account toAccount(){
                return Account.builder()
                        .name(name)
                        .statusMessage(statusMessage)
                        .accountLocation(accountLocation)
                        .build();
            }
        }
    }
}
