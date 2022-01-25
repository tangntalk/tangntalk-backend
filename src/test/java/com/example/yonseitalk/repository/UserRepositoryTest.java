package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup(){
        UserDto user1= UserDto.builder()
                .userId("ji1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user2= UserDto.builder()
                .userId("ji2")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        //when
        userService.save(user1);
        userService.save(user2);
    }

    @Transactional
    @Test
    void findById() {
        //then
        UserDto findUser = userService.findById("ji1").get();
        Assertions.assertThat(findUser.getUserId()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void save() {

        //then
        UserDto findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getUserId()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void delete() {

        userService.deleteById("ji1");

        //then
        UserDto findUser = userService.findById("ji1").orElse(null);
        Assertions.assertThat(findUser).isNull();
    }
    //findByLocation test 추가하기

    @Transactional
    @Test
    void updateStatusMessage() {

        String newMsg = "happy";
        userService.updateStatusMessage("ji1",newMsg);

        //then
        UserDto findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getStatusMessage()).isEqualTo(newMsg);

    }

    @Transactional
    @Test
    void updateUserLocation() {

        String newLocation = "학생회관";
        userService.updateUserLocation("ji1",newLocation);

        //then
        UserDto findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getUserLocation()).isEqualTo(newLocation);

    }

    @Transactional
    @Test
    void updateUserConnectionStatus() {
        Boolean flag = false;
        userService.updateUserConnectionStatus("ji1", flag);

        //then
        UserDto findUser = userService.findById("ji1").get();
        Assertions.assertThat(findUser.getConnectionStatus()).isEqualTo(flag);

    }

    @Test
    @Transactional
    void addFriend(){
        userService.addFriend("ji1", "ji2");

        UserDto user = userService.findById("ji1").get();
        UserDto user2 = userService.findById("ji1").get();
        UserDto friend = userService.findById("ji2").get();

        System.out.println(userService.findFriendUser("ji1"));
        Assertions.assertThat(user.equals(user2)).isEqualTo(true);
        Assertions.assertThat(userService.findFriendUser("ji1").size()).isEqualTo(1);
        Assertions.assertThat(userService.findFriendUser("ji1").contains(friend)).isEqualTo(true);
        Assertions.assertThat(userService.findFriendUser("ji2").size()).isEqualTo(0);
    }

}