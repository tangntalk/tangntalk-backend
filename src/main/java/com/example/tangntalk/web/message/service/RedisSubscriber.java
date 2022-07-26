package com.example.tangntalk.web.message.service;

import com.example.tangntalk.web.message.dto.MessageDto;
import com.example.tangntalk.web.message.dto.response.SingleMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
        String topic = redisTemplate.getStringSerializer().deserialize(message.getChannel());
        try {
            MessageDto messageDto = objectMapper.readValue(publishMessage, MessageDto.class);
            template.convertAndSend("/sub/chat/" + topic, SingleMessageDto.from(messageDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
