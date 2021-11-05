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
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(true);

        User user2= new User();
        user2.setUser_id("nam");
        user2.setName("jihoon");
        user2.setPassword("ddda");
        user2.setStatus_message("hihi");
        user2.setType("1");
        user2.setUser_location("공학관");
        user2.setConnection_status(true);

        User user3= new User();
        user3.setUser_id("pp");
        user3.setName("jihoon");
        user3.setPassword("ddda");
        user3.setStatus_message("hihi");
        user3.setType("1");
        user3.setUser_location("공학관");
        user3.setConnection_status(true);

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
        Assertions.assertThat(friends.size()).isEqualTo(2);
        Assertions.assertThat(friends.stream().map(FriendUser::getUser_id)).contains("pp");
        Assertions.assertThat(friends.stream().map(FriendUser::getUser_id)).contains("nam");
        Assertions.assertThat(friends.stream().map(FriendUser::getIsFriend)).containsOnly(true);
    }

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
        dbUserRepository.save(user1);
        dbUserRepository.save(user2);
        dbUserRepository.save(user3);
        dbUserRepository.save(user4);



        dbFriendRepository.save(friend1);
        dbFriendRepository.save(friend2);
        dbFriendRepository.save(friend3);
        dbFriendRepository.save(friend4);
        dbFriendRepository.save(friend5);
        dbFriendRepository.save(friend6);

        //then
        List<FriendUser> friends = dbFriendUserRepository.search(user2.getUser_id(),"z");
        Assertions.assertThat(friends.size()).isEqualTo(2);
        for (FriendUser friend : friends) {
            System.out.println(friend);
        }

    }


}