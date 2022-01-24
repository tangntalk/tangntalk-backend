package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.friend.dao.DBFriendRepository;
import com.example.yonseitalk.web.friend.dao.Friend;
import com.example.yonseitalk.web.user.dao.SearchUser;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class UserRepositorySearchTest {
    @Autowired
    private DBFriendRepository dbFriendRepository;

    @Autowired
    private UserService userService;

    @Test
    void searchTest() {

        //given
        UserDto user1 = UserDto.builder()
                .userId("aabb")
                .name("zffgg")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user2 = UserDto.builder()
                .userId("bbcc")
                .name("zghh")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user3 = UserDto.builder()
                .userId("ccdd")
                .name("zhhii")
                .password("dda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user4 = UserDto.builder()
                .userId("ddee")
                .name("iijj")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

//
//        Friend friend1=new Friend();
//        friend1.setUserId("aabb");
//        friend1.setFriendId("bbcc");
//
//        Friend friend2=new Friend();
//        friend2.setUserId("aabb");
//        friend2.setFriendId("ccdd");
//
//        Friend friend3=new Friend();
//        friend3.setUserId("aabb");
//        friend3.setFriendId("ddee");
//
//        Friend friend4=new Friend();
//        friend4.setUserId("bbcc");
//        friend4.setFriendId("ccdd");
//
//        Friend friend5=new Friend();
//        friend5.setUserId("bbcc");
//        friend5.setFriendId("ddee");
//
//        Friend friend6=new Friend();
//        friend6.setUserId("ccdd");
//        friend6.setFriendId("ddee");


        //when
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);



//        dbFriendRepository.save(friend1);
//        dbFriendRepository.save(friend2);
//        dbFriendRepository.save(friend3);
//        dbFriendRepository.save(friend4);
//        dbFriendRepository.save(friend5);
//        dbFriendRepository.save(friend6);

        //then
        List<UserDto> friends = userService.search(user2.getUserId(),"z");
        Assertions.assertThat(friends.size()).isEqualTo(2);

    }

}