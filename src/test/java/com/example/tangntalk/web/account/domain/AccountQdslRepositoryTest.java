package com.example.tangntalk.web.account.domain;

import com.example.tangntalk.web.account.dto.response.FriendDto;
import com.example.tangntalk.web.account.dto.response.FriendSearchDto;
import com.example.tangntalk.web.account.repository.AccountQdslRepository;
import com.example.tangntalk.web.account.repository.AccountRepository;
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
        List<FriendSearchDto> dtoList = accountService.searchByNameOrUsername("string", "str");
        for (FriendSearchDto friendSearchDto : dtoList) {
            System.out.println(friendSearchDto);
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