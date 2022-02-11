package com.example.yonseitalk.repository;


import com.example.yonseitalk.web.account.dto.FriendAccount;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
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

        //given

        AccountDto user1 = AccountDto.builder()
                .accountId("tt")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user2 = AccountDto.builder()
                .accountId("nam")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user3 = AccountDto.builder()
                .accountId("pp")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("강사")
                .accountLocation("공학관")
                .connectionStatus(false)
                .build();


        //when
        accountService.save(user1);
        accountService.save(user2);
        accountService.save(user3);

        accountService.addFriend(user1.getAccountId(), user2.getAccountId());
        accountService.addFriend(user1.getAccountId(), user3.getAccountId());

        //then
        List<FriendAccount> friends = accountService.findFriendAccount(user1.getAccountId());
        System.out.println(friends);
        assertThat(friends.size()).isEqualTo(2);
        assertThat(friends.stream().map(FriendAccount::getAccountId)).contains("pp");
        assertThat(friends.stream().map(FriendAccount::getAccountId)).contains("nam");
        assertThat(friends.contains(user2));
        assertThat(friends.contains(user3));
        assertThat(friends.stream().map(FriendAccount::getChatroomId)).containsNull();
        assertThat(friends.stream().map(FriendAccount::getAccountLocation)).contains("공학관");
    }




}