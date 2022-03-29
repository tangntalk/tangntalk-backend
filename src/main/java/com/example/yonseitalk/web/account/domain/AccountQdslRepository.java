package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.dto.FriendSearchResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.yonseitalk.web.account.domain.QAccount.*;

@Repository
@RequiredArgsConstructor
public class AccountQdslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Account> search(String id, String searchQuery){

        return  jpaQueryFactory.selectFrom(account)
                .where(account.accountId.ne(id)
                        .and(account.accountId.contains(searchQuery)
                                .or(account.name.contains(searchQuery))))
                .fetch();

    }

}
