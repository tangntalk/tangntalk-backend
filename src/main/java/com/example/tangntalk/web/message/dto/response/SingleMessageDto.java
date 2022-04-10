package com.example.tangntalk.web.message.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleMessageDto {
    Long messageId;
    Long chatroomId;
    String senderId;
    String content;
    String sendTime;
    String readTime;
    Boolean rendezvousFlag;
    String rendezvousLocation;
    String rendezvousTime;

}
