package com.example.tangntalk.web.account.dto.response;

import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.dto.projection.FriendAccountProjection;
import com.example.tangntalk.web.account.dto.projection.SearchAccountProjection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class FriendDto {

    private String username;
    private String name;
    private String statusMessage;
    private String type;
    private String accountLocation;
    private Boolean connectionStatus;
    private Long chatroomId;

    public static FriendDto from(Account account){
        return FriendDto.builder()
                .username(account.getUsername())
                .name(account.getName())
                .statusMessage(account.getStatusMessage())
                .type(account.getAccountType())
                .accountLocation(account.getAccountLocation())
                .connectionStatus(account.getConnectionStatus())
                .build();
    }

    public static class Request{
        @Data
        public static class addFriend {
            private String friendUsername;
        }

    }
    public static class Response{
        @Data
        public static class FriendQuery {
            public  List<Friend> online;
            public  List<Friend> offline;

            public FriendQuery(List<FriendAccountProjection> projectionList) {
                online = new ArrayList<>();
                offline = new ArrayList<>();
                projectionList.forEach(this::groupFriend);
            }
            public void groupFriend(FriendAccountProjection projection){
                if(projection.getConnectionStatus()){
                    online.add(Friend.fromFriendAccountProjection(projection));
                    return;
                }
                offline.add(Friend.fromFriendAccountProjection(projection));
            }

            @Builder
            @Data
            public static class Friend{
                private String username;
                private String name;
                private String statusMessage;
                private String type;
                private String accountLocation;
                private Long chatroomId;

                public static Friend fromFriendAccountProjection(FriendAccountProjection projection){
                    return Friend.builder()
                            .username(projection.getUsername())
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
        @Builder
        public static class SearchFriend{
            private String username;
            private String name;
            private String statusMessage;
            private String type;
            private Boolean isFriend;

            public static SearchFriend fromProjection(SearchAccountProjection searchAccountProjection){
                return SearchFriend.builder()
                        .username(searchAccountProjection.getUsername())
                        .name(searchAccountProjection.getName())
                        .statusMessage(searchAccountProjection.getStatusMessage())
                        .type(searchAccountProjection.getType())
                        .isFriend(searchAccountProjection.getIsFriend() != null).build();
            }
        }
        @Data
        public static class FriendCheck{
            private final boolean isFriend;

        }

    }
}
