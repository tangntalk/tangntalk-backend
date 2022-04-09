package com.example.tangntalk.web.chatroom.repository;

import com.example.tangntalk.web.chatroom.dto.response.ChatroomDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tangntalk.web.account.domain.QAccount.*;
import static com.example.tangntalk.web.chatroom.domain.QChatroom.*;
import static com.example.tangntalk.web.message.domain.QMessage.*;

@Repository
@RequiredArgsConstructor
public class ChatroomQdslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ChatroomDetailDto> findChatroomListByUser(String userId){
        return jpaQueryFactory
                .select(Projections.constructor(ChatroomDetailDto.class,
                        chatroom.chatroomId,
                        chatroom.user1.username,
                        chatroom.user2.username,
                        chatroom.user1.name,
                        chatroom.user2.name,
                        chatroom.lastMessage.sender.username,
                        chatroom.lastMessage.content,
                        chatroom.lastMessage.sendTime,
                        chatroom.lastMessage.rendezvousFlag,
                        chatroom.lastMessage.rendezvousLocation,
                        chatroom.lastMessage.rendezvousTime,
                        chatroom.user1.connectionStatus,
                        chatroom.user2.connectionStatus))
                        .from(chatroom)
                        .innerJoin(chatroom.user1, account)
                        .innerJoin(chatroom.user2, account)
                        .innerJoin(chatroom.lastMessage, message)
                        .where(chatroom.user1.username.eq(userId)
                                .or(chatroom.user2.username.eq(userId)))
                        .fetch();

    }

}
