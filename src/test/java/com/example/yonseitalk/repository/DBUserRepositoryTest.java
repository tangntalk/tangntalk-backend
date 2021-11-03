package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
@Transactional
class DBUserRepositoryTest {

    @Autowired
    private DBUserRepository dbUserRepository;

    @AfterEach
    void afterEach(){
        dbUserRepository.deleteAll();
    }

    @Test
    void findById() {
        //given
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

        //then
        User findUser=dbUserRepository.findById(user1.getUser_id()).get();

        Assertions.assertThat(findUser.getUser_id()).isEqualTo(user1.getUser_id());
    }

    @Test
    void save() {
        User user1= new User();
        user1.setUser_id("ji2");
        user1.setName("jihoon");
        user1.setPassword("ddda");

        user1.setStatus_message("hihi");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        //when
        dbUserRepository.save(user1);


        //then
        User findUser=dbUserRepository.findById(user1.getUser_id()).get();

        Assertions.assertThat(findUser.getUser_id()).isEqualTo(user1.getUser_id());
    }

    @Test
    void delete() {

        User user1= new User();
        user1.setUser_id("ji2");
        user1.setName("jihoon");
        user1.setPassword("ddda");

        user1.setStatus_message("hihi");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        //when
        dbUserRepository.save(user1);
        dbUserRepository.delete(user1.getUser_id());

        //then
        User findUser=dbUserRepository.findById(user1.getUser_id()).orElse(new User());

        Assertions.assertThat(findUser.getUser_id()).isNull();
    }

    @Test
    void updateStatusMessage() {
        User user1= new User();
        user1.setUser_id("ji2");
        user1.setName("jihoon");
        user1.setPassword("ddda");

        user1.setStatus_message("hihi");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        //when
        dbUserRepository.save(user1);
        String newMsg = "happy";
        dbUserRepository.updateStatusMessage(user1.getUser_id(),newMsg);

        //then
        User findUser=dbUserRepository.findById(user1.getUser_id()).get();
        Assertions.assertThat(findUser.getStatus_message()).isEqualTo(newMsg);

    }

    @Test
    void updateUserLocation() {
        User user1= new User();
        user1.setUser_id("ji2");
        user1.setName("jihoon");
        user1.setPassword("ddda");

        user1.setStatus_message("hihi");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        //when
        dbUserRepository.save(user1);
        String newLocation = "학생회관";
        dbUserRepository.updateUserLocation(user1.getUser_id(),newLocation);

        //then
        User findUser=dbUserRepository.findById(user1.getUser_id()).get();
        Assertions.assertThat(findUser.getUser_location()).isEqualTo(newLocation);

    }
}