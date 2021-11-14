package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;
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
    private DBUserRepository dbUserRepository;

    @BeforeEach
    void setup(){
        User user1= new User();
        user1.setUser_id("ji1");
        user1.setName("jihoon");
        user1.setPassword("ddda");

        user1.setStatus_message("hihi");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        //when
        dbUserRepository.save(user1);
    }

    @Transactional
    @Test
    void findById() {
        //then
        User findUser=dbUserRepository.findById("ji1").get();
        Assertions.assertThat(findUser.getUser_id()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void save() {

        //then
        User findUser=dbUserRepository.findById("ji1").get();

        Assertions.assertThat(findUser.getUser_id()).isEqualTo("ji1");
    }

    @Transactional
    @Test
    void delete() {

        dbUserRepository.delete("ji1");

        //then
        User findUser=dbUserRepository.findById("ji1").orElse(new User());

        Assertions.assertThat(findUser.getUser_id()).isNull();
    }

    @Transactional
    @Test
    void updateStatusMessage() {

        String newMsg = "happy";
        dbUserRepository.updateStatusMessage("ji1",newMsg);

        //then
        User findUser=dbUserRepository.findById("ji1").get();
        Assertions.assertThat(findUser.getStatus_message()).isEqualTo(newMsg);

    }

    @Transactional
    @Test
    void updateUserLocation() {

        String newLocation = "학생회관";
        dbUserRepository.updateUserLocation("ji1",newLocation);

        //then
        User findUser=dbUserRepository.findById("ji1").get();
        Assertions.assertThat(findUser.getUser_location()).isEqualTo(newLocation);

    }

    @Transactional
    @Test
    void updateUserConnectionStatus() {
        Boolean flag= false;
        dbUserRepository.updateUserConnectionStatus("ji1",flag);

        //then
        User findUser=dbUserRepository.findById("ji1").get();
        Assertions.assertThat(findUser.getConnection_status()).isEqualTo(flag);

    }

}