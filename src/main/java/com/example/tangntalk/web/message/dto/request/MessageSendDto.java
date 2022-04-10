package com.example.tangntalk.web.message.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;

@Getter
@Setter
@ToString
public class MessageSendDto {
    private Long chatroomId;
    private String senderId;
    private String content;
}
