package com.example.tangntalk.web.chatroom.controller;


import com.example.tangntalk.util.stomp.StompEventListener;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.message.dto.MessageDto;
import com.example.tangntalk.web.message.dto.request.MessageSendDto;
import com.example.tangntalk.web.message.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final RedisPublisher redisPublisher;
    private final ChatService chatService;
    private final AccountService accountService;

    @MessageMapping(value = "/chat/message")
    public void message(MessageSendDto message){
        String receiverId = message.getReceiverId();
        String senderId = message.getSenderId();
        AccountDto receiverInfo = accountService.findByUsername(receiverId).orElseThrow();

        MessageDto messageDto = chatService.sendMessage(
                message.getSenderId(),
                message.getChatroomId(),
                message.getContent(),
                message.getRendezvousTime());

        publishToRedisChannel(receiverId, messageDto);
        publishToRedisChannel(senderId, messageDto);
    }
    private void publishToRedisChannel(String receiverId, MessageDto message){
        ChannelTopic topic = StompEventListener.topics.get(receiverId);
        if(topic == null){
            topic = new ChannelTopic(receiverId);
            StompEventListener.topics.put(receiverId,topic);
        }
        redisPublisher.publish(topic,message);
    }
}