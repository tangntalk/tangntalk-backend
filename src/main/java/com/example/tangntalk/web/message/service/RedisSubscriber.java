package com.example.tangntalk.web.message.service;

import com.example.tangntalk.web.message.dto.request.MessageSendDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate template;


    @Override
    public void onMessage(Message message, byte[] pattern) {

        String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

        try {
            MessageSendDto messageSendDto = objectMapper.readValue(publishMessage,MessageSendDto.class);
            template.convertAndSend("/sub/chat/" + messageSendDto.getReceiverId(), messageSendDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
