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
        User user1= new User();
        user1.setUser_id("tt");
        user1.setName("jihoon");
        user1.setPassword("ddda");

        user1.setStatus_message("hihi");
        user1.setType("학생");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        User user2= new User();
        user2.setUser_id("nam");
        user2.setName("jihoon");
        user2.setPassword("ddda");

        user2.setStatus_message("hihi");
        user2.setType("학생");
        user2.setUser_location("공학관");
        user2.setConnection_status(true);

        User user3= new User();
        user3.setUser_id("pp");
        user3.setName("jihoon");
        user3.setPassword("ddda");

        user3.setStatus_message("hihi");
        user3.setType("학생");
        user3.setUser_location("공학관");
        user3.setConnection_status(true);

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
