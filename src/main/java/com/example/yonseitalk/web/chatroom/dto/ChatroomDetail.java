package com.example.yonseitalk.web.chatroom.dto;

import com.example.yonseitalk.web.chatroom.dto.projection.ChatroomDetailProjection;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomDetail {
    private Long chatroomId;
    private String user1;
    private String user2;
    private String senderId;
    private String content;
    private Timestamp sendTime;
    private Boolean rendezvousFlag;
    private String rendezvousLocation;
    private Timestamp rendezvousTime;
    private Boolean connectionStatus;

    public static ChatroomDetail fromProjection(ChatroomDetailProjection chatroomDetailProjection){
        return ChatroomDetail.builder()
                .chatroomId(chatroomDetailProjection.getChatroomId())
                .user1(chatroomDetailProjection.getUser1())
                .user2(chatroomDetailProjection.getUser2())
                .senderId(chatroomDetailProjection.getSenderId())
                .content(chatroomDetailProjection.getContent())
                .sendTime(chatroomDetailProjection.getSendTime())
                .rendezvousFlag(chatroomDetailProjection.getRendezvousFlag())
                .rendezvousLocation(chatroomDetailProjection.getRendezvousLocation())
                .rendezvousTime(chatroomDetailProjection.getRendezvousTime())
                .connectionStatus(chatroomDetailProjection.getConnectionStatus())
                .build();
    }
}
