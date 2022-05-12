package com.example.tangntalk.web.account.repository;

import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.dto.response.FriendDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.tangntalk.web.account.domain.QAccount.*;
import static com.example.tangntalk.web.chatroom.domain.QChatroom.*;


@Repository
@RequiredArgsConstructor
public class AccountQdslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Account> search(String id, String searchQuery){

        return  jpaQueryFactory.selectFrom(account)
                .where(account.username.ne(id)
                        .and(account.username.contains(searchQuery)
                                .or(account.name.contains(searchQuery))))
                .fetch();

    }

    public List<FriendDto> friendQuery(Account requestAccount){
        return jpaQueryFactory.select(Projections.constructor(FriendDto.class,
                account.username,
                account.name,
                account.statusMessage,
                account.accountType,
                account.accountLocation,
                account.connectionStatus,
                chatroom.chatroomId))
                .from(account)
                .leftJoin(chatroom)
                .on(account.eq(chatroom.user1).or(account.eq(chatroom.user2)))
                .where(account.friends.contains(requestAccount))
                .fetch();
    }

}
