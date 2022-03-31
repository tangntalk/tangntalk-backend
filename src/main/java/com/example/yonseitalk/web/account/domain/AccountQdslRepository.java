package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.dto.FriendDto;
import com.example.yonseitalk.web.account.dto.FriendSearchResponse;
import com.example.yonseitalk.web.chatroom.domain.QChatroom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.yonseitalk.web.account.domain.QAccount.*;
import static com.example.yonseitalk.web.chatroom.domain.QChatroom.*;

@Repository
@RequiredArgsConstructor
public class AccountQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;



    /*
    @Query(value = "select yt_account.name as name,yt_account.account_id as accountId, " +
            "yt_account.status_message as statusMessage," +
            "yt_account.type as type, yt_account.connection_status as connectionStatus, " +
            "yt_account.account_location as userLocation,chatroom.chatroom_id as chatroomId " +
            "from yt_account join friends on yt_account.account_id=friends.friend_id " +
            "left join chatroom  on friends.account_id in (chatroom.user_1,chatroom.user_2) and " +
            "friends.friend_id in (chatroom.user_1,chatroom.user_2) " +
            "where friends.account_id = ?", nativeQuery = true)



         private String accountId;
    private String name;
    private String statusMessage;
    private String type;
    private String accountLocation;
    private Long chatroomId;


     */
    public List<Account> search(String id, String searchQuery){

        return  jpaQueryFactory.selectFrom(account)
                .where(account.accountId.ne(id)
                        .and(account.accountId.contains(searchQuery)
                                .or(account.name.contains(searchQuery))))
                .fetch();

    }

    public List<FriendDto> friendQuery(Account requestAccount){
        return jpaQueryFactory.select(Projections.bean(FriendDto.class,
                account.accountId,
                account.name,
                account.statusMessage,
                account.type,
                account.accountLocation,
                account.connectionStatus,
                chatroom.chatroomId))
                .from(account)
                .leftJoin(chatroom)
                .on(account.eq(chatroom.user1()).or(account.eq(chatroom.user2())))
                .where(account.friendsAddedAccount.contains(requestAccount))
                .fetch();
    }



}
