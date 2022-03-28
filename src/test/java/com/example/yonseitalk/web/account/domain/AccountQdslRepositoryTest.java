package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.FriendSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AccountQdslRepositoryTest {
    @Autowired
    AccountQdslRepository accountQdslRepository;

    @Test
    void search(){
        List<FriendSearchResponse> dtoList = accountQdslRepository.search("string", "str");
        for (FriendSearchResponse friendSearchResponse : dtoList) {
            System.out.println(friendSearchResponse);
        }

    }

}