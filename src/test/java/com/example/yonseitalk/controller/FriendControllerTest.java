package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.friend.dao.Friend;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.friend.dao.DBFriendRepository;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class FriendControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private DBFriendRepository dbFriendRepository;

    @Autowired
    private UserService userService;


    @Test
    void friendListTest() throws Exception{

        //given
        UserDto user1 = UserDto.builder()
                .userId("tt")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user2 = UserDto.builder()
                .userId("nam")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user3 = UserDto.builder()
                .userId("pp")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("강사")
                .userLocation("공학관")
                .connectionStatus(false)
                .build();

        Friend friend1=new Friend();
        friend1.setFriendId("nam");
        friend1.setUserId("tt");


        Friend friend2 =new Friend();
        friend2.setFriendId("pp");
        friend2.setUserId("tt");

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        dbFriendRepository.save(friend1);
        dbFriendRepository.save(friend2);

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/users/"+user1.getUserId()+"/friends"))
                .andDo(print());

        //then

    }


    @Test
    void searchUserTest() throws Exception{
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


        Friend friend1=new Friend();
        friend1.setUserId("aabb");
        friend1.setFriendId("bbcc");

        Friend friend2=new Friend();
        friend2.setUserId("aabb");
        friend2.setFriendId("ccdd");

        Friend friend3=new Friend();
        friend3.setUserId("aabb");
        friend3.setFriendId("ddee");

        Friend friend4=new Friend();
        friend4.setUserId("bbcc");
        friend4.setFriendId("ccdd");

        Friend friend5=new Friend();
        friend5.setUserId("bbcc");
        friend5.setFriendId("ddee");

        Friend friend6=new Friend();
        friend6.setUserId("ccdd");
        friend6.setFriendId("ddee");

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);

        dbFriendRepository.save(friend1);
        dbFriendRepository.save(friend2);
        dbFriendRepository.save(friend3);
        dbFriendRepository.save(friend4);
        dbFriendRepository.save(friend5);
        dbFriendRepository.save(friend6);

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/users/"+user2.getUserId()
                +"/friends/search?query=z")).andDo(print());
    }

    @Test
    void addFriendTest() throws Exception{

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

        userService.save(user1);
        userService.save(user2);

        //when
        ResultActions resultActions = this.mockMvc.perform(post("/users/"+user1.getUserId() +"/friends")
                .contentType(MediaType.APPLICATION_JSON) .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content("{" + " \"friend_id\" : \""+user2.getUserId()+"\" }"));

        ResultActions resultActions2 = this.mockMvc.perform(get("/users/"+user1.getUserId()+"/friends"))
                .andDo(print());


    }

    @Test
    void delFriendTest() throws Exception{
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

        userService.save(user1);
        userService.save(user2);

//        Friend friend1=new Friend();
//        friend1.setUserId("aabb");
//        friend1.setFriendId("bbcc");
//        dbFriendRepository.save(friend1);

        //when
        ResultActions resultActions = this.mockMvc.perform(delete("/users/"+user1.getUserId()
                +"/friends/"+user2.getUserId()));

        ResultActions resultActions2 = this.mockMvc.perform(get("/users/"+user1.getUserId()+"/friends"))
                .andDo(print());

    }


}