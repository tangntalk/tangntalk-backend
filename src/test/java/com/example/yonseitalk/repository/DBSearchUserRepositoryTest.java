package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.SearchUser;
import com.example.yonseitalk.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class DBSearchUserRepositoryTest {
    @Autowired
    private DBFriendRepository dbFriendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DBSearchUserRepository dbSearchUserRepository;

    @Test
    void searchTest() {

        //given

        User user1= new User();
        user1.setUser_id("aabb");
        user1.setName("zffgg");
        user1.setPassword("ddda");
        user1.setStatus_message("hihi");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        User user2= new User();
        user2.setUser_id("bbcc");
        user2.setName("zghh");
        user2.setPassword("ddda");
        user2.setStatus_message("hihi");
        user2.setType("1");
        user2.setUser_location("공학관");
        user2.setConnection_status(true);

        User user3= new User();
        user3.setUser_id("ccdd");
        user3.setName("zhhii");
        user3.setPassword("ddda");
        user3.setStatus_message("hihi");
        user3.setType("1");
        user3.setUser_location("공학관");
        user3.setConnection_status(true);

        User user4= new User();
        user4.setUser_id("ddee");
        user4.setName("iijj");
        user4.setPassword("ddda");
        user4.setStatus_message("hihi");
        user4.setType("1");
        user4.setUser_location("공학관");
        user4.setConnection_status(true);


        Friend friend1=new Friend();
        friend1.setUser_id("aabb");
        friend1.setFriend_id("bbcc");

        Friend friend2=new Friend();
        friend2.setUser_id("aabb");
        friend2.setFriend_id("ccdd");

        Friend friend3=new Friend();
        friend3.setUser_id("aabb");
        friend3.setFriend_id("ddee");

        Friend friend4=new Friend();
        friend4.setUser_id("bbcc");
        friend4.setFriend_id("ccdd");

        Friend friend5=new Friend();
        friend5.setUser_id("bbcc");
        friend5.setFriend_id("ddee");

        Friend friend6=new Friend();
        friend6.setUser_id("ccdd");
        friend6.setFriend_id("ddee");




        //when
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);



        dbFriendRepository.save(friend1);
        dbFriendRepository.save(friend2);
        dbFriendRepository.save(friend3);
        dbFriendRepository.save(friend4);
        dbFriendRepository.save(friend5);
        dbFriendRepository.save(friend6);

        //then
        List<SearchUser> friends = dbSearchUserRepository.search(user2.getUser_id(),"z");
        Assertions.assertThat(friends.size()).isEqualTo(2);
        for (SearchUser friend : friends) {
            System.out.println(friend);
        }

    }

}