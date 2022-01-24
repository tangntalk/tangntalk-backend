package com.example.yonseitalk.web.chatroom.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class ChatroomDetail {
    private Long chatroomId;
    private String user1;
    private String user2;
//    private Long last_message_id;

    private String senderId;
//    private String receiver_id;
    private String content;
    private Timestamp sendTime;
    private Boolean rendezvousFlag;
    private String rendezvousLocation;
    private Timestamp rendezvousTime;
    private Boolean connectionStatus;
}
