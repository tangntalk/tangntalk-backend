package com.example.tangntalk.web.chatroom.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ChatroomDetailDto {

    private Long chatroomId;
    private String account1Id;
    private String account2Id;
    private String account1Name;
    private String account2Name;
    private String senderId;
    private String content;
    private Date sendTime;
    private Boolean rendezvousFlag;
    private String rendezvousLocation;
    private Date rendezvousTime;
    private Boolean account1ConnectionStatus;
    private Boolean account2ConnectionStatus;

}
