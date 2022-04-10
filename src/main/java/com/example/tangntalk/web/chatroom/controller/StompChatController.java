package com.example.tangntalk.web.chatroom.controller;


import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.message.dto.request.MessageSendDto;
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