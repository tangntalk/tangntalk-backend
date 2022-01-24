package com.example.yonseitalk.web.chatroom.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Chatroom {
    private Long chatroomId;
    private String user1;
    private String user2;
    private Long lastMessageId;
}
