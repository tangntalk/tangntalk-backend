package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import javax.sql.DataSource;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
class DBUserRepositoryTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup(){
        User user1= User.builder()
                .userId("ji1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();
        //when
        userService.save(user1);
    }

    @Transactional
    @Test
    void findById() {
        //then
        User findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getUserId()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void save() {

        //then
        User findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getUserId()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void delete() {

        userService.deleteById("ji1");

        //then
        User findUser=userService.findById("ji1").orElse(null);
        Assertions.assertThat(findUser).isNull();
    }
    //findByLocation test 추가하기

    @Transactional
    @Test
    void updateStatusMessage() {

        String newMsg = "happy";
        userService.updateStatusMessage("ji1",newMsg);

        //then
        User findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getStatusMessage()).isEqualTo(newMsg);

    }

    @Transactional
    @Test
    void updateUserLocation() {

        String newLocation = "학생회관";
        userService.updateUserLocation("ji1",newLocation);

        //then
        User findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getUserLocation()).isEqualTo(newLocation);

    }

    @Transactional
    @Test
    void updateUserConnectionStatus() {
        Boolean flag= false;
        userService.updateUserConnectionStatus("ji1",flag);

        //then
        User findUser=userService.findById("ji1").get();
        Assertions.assertThat(findUser.getConnectionStatus()).isEqualTo(flag);

    }

}