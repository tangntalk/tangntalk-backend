package com.example.tangntalk.util.stomp;

import com.example.tangntalk.web.account.service.AccountService;
import com.example.tangntalk.web.message.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompEventListener {

    private final AccountService accountService;
    private final RedisSubscriber redisSubscriber;
    private final RedisMessageListenerContainer redisMessageListener;


    public static final Map<String, ChannelTopic> topics = new ConcurrentHashMap<>();

    public static final Map<String, String> sessions = new ConcurrentHashMap<>();



    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String subscriptionURL = headers.getDestination();

        if(subscriptionURL == null || !subscriptionURL.startsWith("/sub/chat/")){
            return;
        }
        String accountId = subscriptionURL.substring(10);
        if(accountService.findByUsername(accountId).isEmpty()){
            return;
        }
        accountService.updateAccountConnectionStatus(accountId, true);
        ChannelTopic topic = topics.get(accountId);
        if (topic == null)
            topic = new ChannelTopic(accountId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(accountId, topic);
        sessions.put(headers.getSessionId(),accountId);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event){
        String sessionId = SimpMessageHeaderAccessor.wrap(event.getMessage()).getSessionId();
        if(sessions.containsKey(sessionId)){
            String accountId = sessions.get(sessionId);
            accountService.updateAccountConnectionStatus(accountId, false);
            redisMessageListener.removeMessageListener(redisSubscriber, topics.get(accountId));
            sessions.remove(sessionId);
            topics.remove(accountId);
        }
    }


}
