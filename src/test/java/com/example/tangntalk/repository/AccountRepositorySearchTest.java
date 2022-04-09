package com.example.tangntalk.repository;

import com.example.tangntalk.web.account.repository.AccountRepository;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
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
                .username("aabb")
                .name("zffgg")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user2 = AccountDto.builder()
                .username("bbcc")
                .name("zghh")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user3 = AccountDto.builder()
                .username("ccdd")
                .name("zhhii")
                .password("dda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user4 = AccountDto.builder()
                .username("ddee")
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

        accountService.addFriend(user2.getUsername(), user3.getUsername());

        //then
        //List<SearchAccount> friends = accountService.search(user2.getUsername(),"z");
        //System.out.println(friends);
        //Assertions.assertThat(friends.size()).isEqualTo(2);
    }
}