package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.friend.dao.DBFriendRepository;
import com.example.yonseitalk.web.friend.dao.Friend;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.FriendUser;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@SpringBootTest
class UserRepositoryFriendTest {

//    @Autowired
//    private DBFriendRepository dbFriendRepository;

    @Autowired
    private UserService userService;




    @Test
    @Transactional
    void save(){

        UserDto userDto = userService.findById("tt").get();
        Assertions.assertThat(userService.findFriendUser("tt").size()).isEqualTo(2);
        Assertions.assertThat(userService.findFriendUser("pp").size()).isEqualTo(1);
        Assertions.assertThat(userService.findFriendUser("nam").size()).isEqualTo(0);

    }

    @Test
    @Transactional
    void delete(){

        userService.delFriend("tt", "pp");
        Assertions.assertThat(userService.findFriendUser("tt").size()).isEqualTo(1);

    }

    @BeforeEach
    void setup(){
        UserDto user1 = UserDto.builder()
                .userId("tt")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user2 = UserDto.builder()
                .userId("nam")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user3 = UserDto.builder()
                .userId("pp")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("강사")
                .userLocation("공학관")
                .connectionStatus(false)
                .build();


        //when
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        userService.addFriend("tt", "pp");
        userService.addFriend("tt", "nam");
        userService.addFriend("pp", "nam");

    }
}
