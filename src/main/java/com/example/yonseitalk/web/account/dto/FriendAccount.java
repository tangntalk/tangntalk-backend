package com.example.yonseitalk.web.account.dto;

import com.example.yonseitalk.web.account.dto.projection.FriendAccountProjection;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendAccount {
    private String name;
    private String accountId;
    private String statusMessage;
    private String type;
    private Boolean connectionStatus;
    private String accountLocation;
    private Long chatroomId;

    public static FriendAccount fromProjection(FriendAccountProjection friendAccountProjection){
        return FriendAccount.builder()
                .name(friendAccountProjection.getName())
                .accountId(friendAccountProjection.getAccountId())
                .statusMessage(friendAccountProjection.getStatusMessage())
                .type(friendAccountProjection.getType())
                .connectionStatus(friendAccountProjection.getConnectionStatus())
                .accountLocation(friendAccountProjection.getAccountLocation())
                .chatroomId(friendAccountProjection.getChatroomId())
                .build();
    }
}
