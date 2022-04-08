package com.example.tangntalk.repository;

import com.example.tangntalk.exception.NotFoundException;
import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.domain.AccountRepository;
import com.example.tangntalk.web.account.service.AccountService;
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

    @Autowired
    private AccountRepository accountRepository;


    @Test
    @Transactional
    void save(){

        Account account = accountRepository.findById("tt").orElseThrow(NotFoundException::new);

        Assertions.assertThat(accountService.findFriendAccount("tt").getOnline().size()).isEqualTo(1);
        Assertions.assertThat(accountService.findFriendAccount("tt").getOffline().size()).isEqualTo(1);
        Assertions.assertThat(accountService.findFriendAccount("pp").getOnline().size()).isEqualTo(1);
        Assertions.assertThat(accountService.findFriendAccount("nam").getOnline().size()).isEqualTo(0);

    }

    @Test
    @Transactional
    void delete(){

        accountService.deleteFriend("tt", "pp");
        log.info(accountService.findFriendAccount("tt").toString());
        Assertions.assertThat(accountService.findFriendAccount("tt").getOffline().size()).isEqualTo(0);

    }

    @BeforeEach
    void setup(){
        Account account1 = new Account();
        account1.setUsername("tt");
        account1.setName("jihoon");
        account1.setPassword("ddda");
        account1.setStatusMessage("hihi");
        account1.setAccountType("학생");
        account1.setAccountLocation("공학관");
        account1.setConnectionStatus(true);

        Account account2 = new Account();
        account2.setUsername("nam");
        account2.setName("jihoon");
        account2.setPassword("ddda");
        account2.setStatusMessage("hihi");
        account2.setAccountType("학생");
        account2.setAccountLocation("공학관");
        account2.setConnectionStatus(true);


        Account account3 = new Account();
        account3.setUsername("pp");
        account3.setName("jihoon");
        account3.setPassword("ddda");
        account3.setStatusMessage("hihi");
        account3.setAccountType("강사");
        account3.setAccountLocation("공학관");
        account3.setConnectionStatus(false);


        //when
        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.save(account3);

        accountService.addFriend("tt", "pp");
        accountService.addFriend("tt", "nam");
        accountService.addFriend("pp", "nam");

    }
}
