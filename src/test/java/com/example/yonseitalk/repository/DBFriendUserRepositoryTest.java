package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.FriendUser;
import com.example.yonseitalk.domain.User;
import org.assertj.core.api.Assertions;
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
    private DBFriendRepository dbFriendRepository;

    @Autowired
    private DBUserRepository dbUserRepository;

    @Autowired
    private DBFriendUserRepository dbFriendUserRepository;

    @Test
    void findAllTest() {

        //given

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
        user2.setType("일반");
        user2.setUser_location("공학관");
        user2.setConnection_status(true);

        User user3= new User();
        user3.setUser_id("pp");
        user3.setName("jihoon");
        user3.setPassword("ddda");
        user3.setStatus_message("hihi");
        user3.setType("강사");
        user3.setUser_location("공학관");
        user3.setConnection_status(false);

        Friend friend1=new Friend();
        friend1.setFriend_id("nam");
        friend1.setUser_id("tt");


        Friend friend2 =new Friend();
        friend2.setFriend_id("pp");
        friend2.setUser_id("tt");

        //when
        dbUserRepository.save(user1);
        dbUserRepository.save(user2);
        dbUserRepository.save(user3);


        dbFriendRepository.save(friend1);
        dbFriendRepository.save(friend2);

        //then
        List<FriendUser> friends = dbFriendUserRepository.findAll(user1.getUser_id());
        assertThat(friends.size()).isEqualTo(2);
        assertThat(friends.stream().map(FriendUser::getUser_id)).contains("pp");
        assertThat(friends.stream().map(FriendUser::getUser_id)).contains("nam");
        assertThat(friends.stream().map(FriendUser::getChatroomId)).containsNull();
        assertThat(friends.stream().map(FriendUser::getUser_location)).contains("공학관");
    }




}