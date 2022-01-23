package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.repository.DBFriendRepository;
import com.example.yonseitalk.repository.DBFriendUserRepository;
import com.example.yonseitalk.repository.UserRepository;
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
    private UserRepository userRepository;


    @Test
    void friendListTest() throws Exception{

        //given
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

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

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

        //when
        ResultActions resultActions = this.mockMvc.perform(get("/users/"+user2.getUserId()
                +"/friends/search?query=z")).andDo(print());
    }

    @Test
    void addFriendTest() throws Exception{

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

        userRepository.save(user1);
        userRepository.save(user2);

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

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend1=new Friend();
        friend1.setUser_id("aabb");
        friend1.setFriend_id("bbcc");
        dbFriendRepository.save(friend1);

        //when
        ResultActions resultActions = this.mockMvc.perform(delete("/users/"+user1.getUserId()
                +"/friends/"+user2.getUserId()));

        ResultActions resultActions2 = this.mockMvc.perform(get("/users/"+user1.getUserId()+"/friends"))
                .andDo(print());

    }


}