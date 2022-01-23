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
        User user1 = User.builder()
                .userId("aabb")
                .name("zffgg")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        User user2 = User.builder()
                .userId("bbcc")
                .name("zghh")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        User user3 = User.builder()
                .userId("ccdd")
                .name("zhhii")
                .password("dda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        User user4 = User.builder()
                .userId("ddee")
                .name("iijj")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();


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
        List<SearchUser> friends = dbSearchUserRepository.search(user2.getUserId(),"z");
        Assertions.assertThat(friends.size()).isEqualTo(2);
        for (SearchUser friend : friends) {
            System.out.println(friend);
        }

    }

}