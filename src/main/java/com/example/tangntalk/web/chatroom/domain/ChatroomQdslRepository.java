package com.example.tangntalk.web.chatroom.domain;

import com.example.tangntalk.web.account.domain.QAccount;
import com.example.tangntalk.web.chatroom.domain.QChatroom;
import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import com.example.tangntalk.web.message.domain.QMessage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatroomQdslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ChatroomDto.ChatroomDetail> findChatroomListbyUser(String userId){
        return jpaQueryFactory
                .select(Projections.constructor(ChatroomDto.ChatroomDetail.class,
                        QChatroom.chatroom.chatroomId,
                        QChatroom.chatroom.user1.username,
                        QChatroom.chatroom.user2.username,
                        QChatroom.chatroom.user1.name,
                        QChatroom.chatroom.user2.name,
                        QChatroom.chatroom.lastMessage.sender().username,
                        QChatroom.chatroom.lastMessage.content,
                        QChatroom.chatroom.lastMessage.sendTime,
                        QChatroom.chatroom.lastMessage.rendezvousFlag,
                        QChatroom.chatroom.lastMessage.rendezvousLocation,
                        QChatroom.chatroom.lastMessage.rendezvousTime,
                        QChatroom.chatroom.user1.connectionStatus,
                        QChatroom.chatroom.user2.connectionStatus))
                        .from(QChatroom.chatroom)
                        .innerJoin(QChatroom.chatroom.user1, QAccount.account)
                        .innerJoin(QChatroom.chatroom.user2, QAccount.account)
                        .innerJoin(QChatroom.chatroom.lastMessage, QMessage.message)
                        .where(QChatroom.chatroom.user1.username.eq(userId)
                                .or(QChatroom.chatroom.user2.username.eq(userId)))
                        .fetch();

    }

}
