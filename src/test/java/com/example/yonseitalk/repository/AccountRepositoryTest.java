package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setup(){
        AccountDto user1= AccountDto.builder()
                .accountId("ji1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user2= AccountDto.builder()
                .accountId("ji2")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        //when
        accountService.save(user1);
        accountService.save(user2);
    }

    @Transactional
    @Test
    void findById() {
        //then
        AccountDto findUser = accountService.findById("ji1").get();
        Assertions.assertThat(findUser.getAccountId()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void save() {

        //then
        AccountDto findUser= accountService.findById("ji1").get();
        Assertions.assertThat(findUser.getAccountId()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void delete() {

        accountService.deleteById("ji1");

        //then
        AccountDto findUser = accountService.findById("ji1").orElse(null);
        Assertions.assertThat(findUser).isNull();
    }
    //findByLocation test 추가하기

    @Transactional
    @Test
    void updateStatusMessage() {

        String newMsg = "happy";
        accountService.updateStatusMessage("ji1",newMsg);

        //then
        AccountDto findUser= accountService.findById("ji1").get();
        Assertions.assertThat(findUser.getStatusMessage()).isEqualTo(newMsg);

    }

    @Transactional
    @Test
    void updateUserLocation() {

        String newLocation = "학생회관";
        accountService.updateAccountLocation("ji1",newLocation);

        //then
        AccountDto findUser= accountService.findById("ji1").get();
        Assertions.assertThat(findUser.getAccountLocation()).isEqualTo(newLocation);

    }

    @Transactional
    @Test
    void updateUserConnectionStatus() {
        Boolean flag = false;
        accountService.updateAccountConnectionStatus("ji1", flag);

        //then
        AccountDto findUser = accountService.findById("ji1").get();
        Assertions.assertThat(findUser.getConnectionStatus()).isEqualTo(flag);

    }

    @Test
    @Transactional
    void addFriend(){
        accountService.addFriend("ji1", "ji2");

        AccountDto user = accountService.findById("ji1").get();
        AccountDto friend = accountService.findById("ji2").get();

        System.out.printf("User Friend List: " + accountService.findFriendAccount("ji1") + "\n");

        System.out.printf("Friend User: " + friend + "\n");
//        Assertions.assertThat(accountService.findFriendAccount("ji1").size()).isEqualTo(1);
//        Assertions.assertThat(accountService.findFriendAccount("ji1").stream().map(FriendAccount::getAccountId).collect(Collectors.toSet()).contains("ji2")).isEqualTo(true);
//        Assertions.assertThat(accountService.findFriendAccount("ji2").size()).isEqualTo(0);
    }

}