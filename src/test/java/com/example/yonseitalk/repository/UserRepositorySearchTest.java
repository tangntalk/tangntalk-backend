package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.friend.dao.DBFriendRepository;
import com.example.yonseitalk.web.user.dto.SearchUser;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class UserRepositorySearchTest {
    @Autowired
    private DBFriendRepository dbFriendRepository;

    @Autowired
    private UserService userService;

    @Test
    void searchTest() {

        //given
        UserDto user1 = UserDto.builder()
                .userId("aabb")
                .name("zffgg")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user2 = UserDto.builder()
                .userId("bbcc")
                .name("zghh")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user3 = UserDto.builder()
                .userId("ccdd")
                .name("zhhii")
                .password("dda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user4 = UserDto.builder()
                .userId("ddee")
                .name("iijj")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();


        //when
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);

        userService.addFriend(user2.getUserId(), user3.getUserId());

        //then
        List<SearchUser> friends = userService.search(user2.getUserId(),"z");
        System.out.println(friends);
        Assertions.assertThat(friends.size()).isEqualTo(2);
    }
}