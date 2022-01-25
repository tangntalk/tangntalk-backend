package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.friend.dao.DBFriendRepository;
import com.example.yonseitalk.web.friend.dao.DBFriendUserRepository;
import com.example.yonseitalk.web.friend.dao.Friend;
import com.example.yonseitalk.web.user.dto.FriendUser;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class DBFriendUserRepositoryTest {

    @Autowired
    private UserService userService;

    @Test
    void findAllTest() {

        //given

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

        userService.addFriend(user1.getUserId(), user2.getUserId());
        userService.addFriend(user1.getUserId(), user3.getUserId());

        //then
        List<FriendUser> friends = userService.findFriendUser(user1.getUserId());
        System.out.println(friends);
        assertThat(friends.size()).isEqualTo(2);
        assertThat(friends.stream().map(FriendUser::getUserId)).contains("pp");
        assertThat(friends.stream().map(FriendUser::getUserId)).contains("nam");
        assertThat(friends.contains(user2));
        assertThat(friends.contains(user3));
        assertThat(friends.stream().map(FriendUser::getChatroomId)).containsNull();
        assertThat(friends.stream().map(FriendUser::getUserLocation)).contains("공학관");
    }




}