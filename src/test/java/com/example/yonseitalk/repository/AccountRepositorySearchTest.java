package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.account.domain.AccountRepository;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AccountRepositorySearchTest {


    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void searchTest() {

        //given
        AccountDto user1 = AccountDto.builder()
                .accountId("aabb")
                .name("zffgg")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user2 = AccountDto.builder()
                .accountId("bbcc")
                .name("zghh")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user3 = AccountDto.builder()
                .accountId("ccdd")
                .name("zhhii")
                .password("dda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user4 = AccountDto.builder()
                .accountId("ddee")
                .name("iijj")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();


        //when
//        accountService.save(user1);
//        accountService.save(user2);
//        accountService.save(user3);
//        accountService.save(user4);

        accountService.addFriend(user2.getAccountId(), user3.getAccountId());

        //then
        //List<SearchAccount> friends = accountService.search(user2.getAccountId(),"z");
        //System.out.println(friends);
        //Assertions.assertThat(friends.size()).isEqualTo(2);
    }
}