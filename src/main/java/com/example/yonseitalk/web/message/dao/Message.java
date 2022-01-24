package com.example.yonseitalk.web.message.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Message {
    private Long messageId;
    private Long chatroomId;
    private String senderId;
    private String content;
    private Timestamp sendTime;
    private Timestamp readTime;
    private Boolean rendezvousFlag;
    private String rendezvousLocation;
    private Timestamp rendezvousTime;


}
