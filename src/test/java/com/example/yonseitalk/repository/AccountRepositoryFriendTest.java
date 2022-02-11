package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class AccountRepositoryFriendTest {


    @Autowired
    private AccountService accountService;


    @Test
    @Transactional
    void save(){

        AccountDto accountDto = accountService.findById("tt").get();
        Assertions.assertThat(accountService.findFriendAccount("tt").size()).isEqualTo(2);
        Assertions.assertThat(accountService.findFriendAccount("pp").size()).isEqualTo(1);
        Assertions.assertThat(accountService.findFriendAccount("nam").size()).isEqualTo(0);

    }

    @Test
    @Transactional
    void delete(){

        accountService.delFriend("tt", "pp");
        log.info(accountService.findFriendAccount("tt").toString());
        Assertions.assertThat(accountService.findFriendAccount("tt").size()).isEqualTo(1);

    }

    @BeforeEach
    void setup(){
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

        accountService.addFriend("tt", "pp");
        accountService.addFriend("tt", "nam");
        accountService.addFriend("pp", "nam");

    }
}
