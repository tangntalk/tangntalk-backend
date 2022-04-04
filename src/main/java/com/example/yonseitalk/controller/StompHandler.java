package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final AccountService accountService;

    private static final Map<String, String> connectAccountMap = new ConcurrentHashMap<>();





    @Override
    public Message preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        if(accessor.getCommand()==null){
            return message;
        }

        switch (accessor.getCommand()) {
            case CONNECT:
                String accountId = accessor.getHost();
                if (accountService.findById(accountId).isPresent()) {
                    connectAccountMap.put(sessionId, accountId);
                    accountService.updateAccountConnectionStatus(accountId, true);
                }
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                break;
            case DISCONNECT:
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
                if (connectAccountMap.containsKey(sessionId)) {
                    accountService.updateAccountConnectionStatus(connectAccountMap.get(sessionId), false);
                }
                break;
            default:
                break;
        }
        return message;
    }

}

