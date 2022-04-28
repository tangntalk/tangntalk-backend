package com.example.tangntalk.web.chatroom.controller;


import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.message.dto.request.MessageSendDto;
import com.example.tangntalk.web.message.service.RedisPublisher;
import com.example.tangntalk.web.message.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final RedisPublisher redisPublisher;
    private final RedisSubscriber redisSubscriber;

    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final ChatService chatService;

    private static final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();



    @MessageMapping(value = "/chat/message")
    public void message(MessageSendDto message){
        //chatService.sendMessage(message.getSenderId(), message.getChatroomId(), message.getContent(), message.getRendezvousTime());
        ChannelTopic topic = topics.get(message.getReceiverId());
        if (topic == null)
            topic = new ChannelTopic(message.getReceiverId());
        redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
        topics.put(message.getReceiverId(), topic);
        redisPublisher.publish(topic,message);
        log.info("send{}",message.getReceiverId());
    }
}