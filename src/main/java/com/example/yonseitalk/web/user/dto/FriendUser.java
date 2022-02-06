package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.user.dto.projection.FriendUserProjection;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendUser{
    private String name;
    private String userId;
    private String statusMessage;
    private String type;
    private Boolean connectionStatus;
    private String userLocation;
    private Long chatroomId;

    public static FriendUser fromProjection(FriendUserProjection friendUserProjection){
        return FriendUser.builder()
                .name(friendUserProjection.getName())
                .userId(friendUserProjection.getUserId())
                .statusMessage(friendUserProjection.getStatusMessage())
                .type(friendUserProjection.getType())
                .connectionStatus(friendUserProjection.getConnectionStatus())
                .userLocation(friendUserProjection.getUserLocation())
                .chatroomId(friendUserProjection.getChatroomId())
                .build();
    }
}
