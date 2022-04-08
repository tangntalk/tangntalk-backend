package com.example.tangntalk.web.account.domain;

import com.example.tangntalk.web.account.dto.FriendDto;
import com.example.tangntalk.web.account.dto.FriendSearchResponse;
import com.example.tangntalk.web.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AccountQdslRepositoryTest {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountQdslRepository accountQdslRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void search(){
        List<FriendSearchResponse> dtoList = accountService.searchByNameOrUsername("string", "str");
        for (FriendSearchResponse friendSearchResponse : dtoList) {
            System.out.println(friendSearchResponse);
        }

    }

    @Test
    void query(){
        List<FriendDto> friendDtoList = accountQdslRepository.friendQuery(accountRepository.getById("string22"));
        for (FriendDto friendDto : friendDtoList) {
            System.out.println("friendDto = " + friendDto);
        }
    }

}