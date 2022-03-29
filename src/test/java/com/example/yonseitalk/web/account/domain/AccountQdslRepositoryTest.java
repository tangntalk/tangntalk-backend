package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.FriendSearchResponse;
import com.example.yonseitalk.web.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AccountQdslRepositoryTest {
    @Autowired
    AccountService accountService;

    @Test
    void search(){
        List<FriendSearchResponse> dtoList = accountService.search("string", "str");
        for (FriendSearchResponse friendSearchResponse : dtoList) {
            System.out.println(friendSearchResponse);
        }

    }

}