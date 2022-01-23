package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DBFriendRepositoryTest {

    @Autowired
    private DBFriendRepository dbFriendRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    void findByUserId() {

        //then
        List<Friend> findFriends=dbFriendRepository.findByUserId("tt");

        Assertions.assertThat(findFriends.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void findByFriendId() {

        //then
        List<Friend> findFriends=dbFriendRepository.findByFriendId("nam");

        Assertions.assertThat(findFriends.size()).isEqualTo(2);

    }

    @Test
    @Transactional
    void save(){

        //then
        List<Friend> findFriends=dbFriendRepository.findByUserId("tt");

        Assertions.assertThat(findFriends.get(0).getFriend_id()).isEqualTo("nam");
        Assertions.assertThat(findFriends.get(1).getFriend_id()).isEqualTo("pp");

    }

    @Test
    @Transactional
    void delete(){
        Friend delFriend = new Friend();
        delFriend.setFriend_id("pp");
        delFriend.setUser_id("tt");
        dbFriendRepository.delete(delFriend);

        //then
        List<Friend> findFriends=dbFriendRepository.findByUserId("tt");

        Assertions.assertThat(findFriends.size()).isEqualTo(1);

    }

    @BeforeEach
    void setup(){
        User user1 = User.builder()
                .userId("tt")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        User user2 = User.builder()
                .userId("nam")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        User user3 = User.builder()
                .userId("pp")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("강사")
                .userLocation("공학관")
                .connectionStatus(false)
                .build();

        Friend friend1=new Friend();
        friend1.setFriend_id("nam");
        friend1.setUser_id("tt");



        Friend friend2 =new Friend();
        friend2.setFriend_id("pp");
        friend2.setUser_id("tt");

        Friend friend3 =new Friend();
        friend3.setFriend_id("nam");
        friend3.setUser_id("pp");

        //when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);


        dbFriendRepository.save(friend1);
        dbFriendRepository.save(friend2);
        dbFriendRepository.save(friend3);
    }
}
