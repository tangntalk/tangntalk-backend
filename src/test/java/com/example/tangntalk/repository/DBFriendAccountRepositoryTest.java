package com.example.tangntalk.repository;

import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.dto.FriendDto;
import com.example.tangntalk.web.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class DBFriendAccountRepositoryTest {

    @Autowired
    private AccountService accountService;

    @Test
    void findAllTest() {

//        //given
//
//        AccountDto user1 = AccountDto.builder()
//                .username("tt")
//                .name("jihoon")
//                .password("ddda")
//                .statusMessage("hihi")
//                .type("학생")
//                .accountLocation("공학관")
//                .connectionStatus(true)
//                .build();
//
//        AccountDto user2 = AccountDto.builder()
//                .username("nam")
//                .name("jihoon")
//                .password("ddda")
//                .statusMessage("hihi")
//                .type("일반")
//                .accountLocation("공학관")
//                .connectionStatus(true)
//                .build();
//
//        AccountDto user3 = AccountDto.builder()
//                .username("pp")
//                .name("jihoon")
//                .password("ddda")
//                .statusMessage("hihi")
//                .type("강사")
//                .accountLocation("공학관")
//                .connectionStatus(false)
//                .build();
//
//
//        //when
//        accountService.save(user1);
//        accountService.save(user2);
//        accountService.save(user3);
//
//        accountService.addFriend(user1.getUsername(), user2.getUsername());
//        accountService.addFriend(user1.getUsername(), user3.getUsername());
//
//        //then
//        FriendDto.Response.FriendQuery friends = accountService.findFriendAccount(user1.getUsername());
//        System.out.println(friends);
//        assertThat(friends.getOffline().size() + friends.getOnline().size()).isEqualTo(2);
//        assertThat(friends.stream().map(FriendAccount::getAccountId)).contains("pp");
//        assertThat(friends.stream().map(FriendAccount::getAccountId)).contains("nam");
//        assertThat(friends.contains(user2));
//        assertThat(friends.contains(user3));
//        assertThat(friends.stream().map(FriendAccount::getChatroomId)).containsNull();
//        assertThat(friends.stream().map(FriendAccount::getAccountLocation)).contains("공학관");
    }




}