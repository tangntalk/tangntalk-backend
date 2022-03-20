package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.dto.projection.FriendAccountProjection;
import com.example.yonseitalk.web.account.dto.projection.NearByAccountProjection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class AccountDto {

    private String accountId;
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
        public static class Login{
            private String accountId;
            private String password;
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

        @Data
        public static class NearBy {
            private final List<NearByAccount> online;
            private final List<NearByAccount> offline;

            public NearBy(List<NearByAccountProjection> projectionList) {
                online = new ArrayList<>();
                offline = new ArrayList<>();
                projectionList.forEach(this::groupFriend);
            }

            public void groupFriend(NearByAccountProjection projection) {
                if (projection.getConnectionStatus()) {
                    online.add(NearByAccount.fromProjection(projection));
                    return;
                }
                offline.add(NearByAccount.fromProjection(projection));
            }

            @Builder
            public static class NearByAccount {
                private String accountId;
                private String name;
                private String statusMessage;
                private String type;
                private String accountLocation;
                private Long chatroomId;

                public static NearByAccount fromProjection(NearByAccountProjection projection) {
                    return NearByAccount.builder()
                            .accountId(projection.getAccountId())
                            .name(projection.getName())
                            .statusMessage(projection.getStatusMessage())
                            .type(projection.getType())
                            .accountLocation(projection.getAccountLocation())
                            .chatroomId(projection.getChatroomId())
                            .build();
                }
            }
        }

        @Data
        public static class Login{
            private String accountId;
            private String token;

            public Login(String loginId, String token) {
                this.accountId = loginId;
                this.token = token;
            }
        }
    }
}
