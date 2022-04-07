package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.chatroom.service.ChatService;
import com.example.yonseitalk.web.message.dto.MessageSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    private final ChatService chatService;

    @MessageMapping(value = "/chat/message")
    public void message(MessageSendDto message){
        chatService.sendMessage(message.getSenderId(), message.getChatroomId(), message.getContent(),-1L);
        template.convertAndSend("/sub/chat/room/" + message.getChatroomId(), message);
    }
}