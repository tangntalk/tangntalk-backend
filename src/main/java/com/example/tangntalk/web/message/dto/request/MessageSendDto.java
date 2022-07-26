package com.example.tangntalk.web.message.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageSendDto {
    private Long chatroomId;
    private String senderId;
    private String receiverId;
    private String content;
    private Long rendezvousTime;
}
