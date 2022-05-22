package com.example.tangntalk.web.chatroom.controller;


import com.example.tangntalk.util.stomp.StompEventListener;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.message.dto.request.MessageSendDto;
import com.example.tangntalk.web.message.service.RedisPublisher;
import com.example.tangntalk.web.message.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
        AccountDto receiverInfo = accountService.findByUsername(receiverId).orElseThrow();

        if(receiverInfo.getConnectionStatus()){
            publishToReceiver(receiverId,message);
        }

        //todo : 비동기 처리 필요 (db에 메시지 저장)
//        chatService.sendMessage(
//                message.getSenderId(),
//                message.getChatroomId(),
//                message.getContent(),
//                message.getRendezvousTime());

    }
    private void publishToReceiver(String receiverId, MessageSendDto message){
        ChannelTopic topic = StompEventListener.topics.get(receiverId);
        if(topic == null){
            topic = new ChannelTopic(receiverId);
            StompEventListener.topics.put(receiverId,topic);
        }
        redisPublisher.publish(topic,message);
    }
}