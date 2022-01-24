package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.chatroom.dao.Chatroom;
import com.example.yonseitalk.web.user.dao.User;
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

    public static FriendUser valueOf(User user, Chatroom chatroom){
        return FriendUser.builder()
                .name(user.getName())
                .statusMessage(user.getStatusMessage())
                .type(user.getType())
                .connectionStatus(user.getConnectionStatus())
                .userLocation(user.getUserLocation())
                .chatroomId(chatroom.getChatroomId())
                .build();
    }
}
