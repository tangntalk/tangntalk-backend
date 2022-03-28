package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.FriendSearchResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.yonseitalk.web.account.domain.QAccount.*;

@Repository
@RequiredArgsConstructor
public class AccountQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<FriendSearchResponse> search(String id, String searchQuery){
        List<Account> accountList = jpaQueryFactory.selectFrom(account)
                .where(account.accountId.ne(id)
                        .and(account.accountId.contains(searchQuery)
                                .or(account.name.contains(searchQuery))))
                .fetch();
        List<FriendSearchResponse> friendSearchResponseList = new ArrayList<>();
        List<String> friendList = getFriendList(id);
        for (Account account1 : accountList) {
            friendSearchResponseList.add(FriendSearchResponse.fromAccount(account1,
                    friendList.contains(account1.getAccountId())));
        }

        return friendSearchResponseList;
    }
    private List<String> getFriendList(String id){
        Account requestAccount = jpaQueryFactory.selectFrom(account)
                .where(account.accountId.eq(id)).fetchFirst();

        return jpaQueryFactory
                .select(account.accountId)
                .from(account)
                .where(account.friendsAddedAccount.contains(requestAccount))
                .fetch();

    }
}
