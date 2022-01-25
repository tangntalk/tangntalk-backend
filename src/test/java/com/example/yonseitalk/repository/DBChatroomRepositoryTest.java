package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.chatroom.dao.Chatroom;
import com.example.yonseitalk.web.chatroom.dao.DBChatroomRepository;
import com.example.yonseitalk.web.message.dao.DBMessageRepository;
import com.example.yonseitalk.web.message.dao.Message;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DBChatroomRepositoryTest {

    @Autowired
    private DBChatroomRepository dbChatroomRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DBMessageRepository dbMessageRepository;

    Long chatroom_id;

    @BeforeEach
    void setup(){

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


        userService.save(user1);
        userService.save(user2);
        Chatroom chatroom = new Chatroom();
        chatroom.setChatroomId(null);
        chatroom.setUser1("flaxinger1");
        chatroom.setUser2("flaxinger2");
        chatroom_id = dbChatroomRepository.save(chatroom).getChatroomId();
    }

//    @Transactional
//    @Test
//    void addChatroom(){
//        Optional<Chatroom> chatroom = dbChatroomRepository.findById(4L);
//        Assertions.assertThat(chatroom.isPresent());
//        Assertions.assertThat(4L).isEqualTo(chatroom.get().getChatroomId());
//    }

    @Transactional
    @Test
    void save(){

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


        Chatroom chatroom = new Chatroom();
        chatroom.setChatroomId(null);
        chatroom.setUser1("tt");
        chatroom.setUser2("nam");
        chatroom_id = dbChatroomRepository.save(chatroom).getChatroomId();

        Optional<Chatroom> chatroom1 =dbChatroomRepository.findById(chatroom_id);
        Assertions.assertThat(chatroom1.get().getUser1()).isEqualTo("tt");



    }

    @Transactional
    @Test
    void findByUserandfindById(){

        List<Chatroom> chatroomList1 = dbChatroomRepository.findByUser("tt");
        List<Chatroom> chatroomList2 = dbChatroomRepository.findByUser("nam");
        Assertions.assertThat(chatroomList1.size() == 1);
        Assertions.assertThat(chatroomList2.size() == 1);
        Optional<Chatroom> chatroom = dbChatroomRepository.findById(chatroomList1.get(0).getChatroomId());

        Assertions.assertThat(chatroom.isPresent());
        Assertions.assertThat(chatroomList1.get(0).getChatroomId()).isEqualTo(chatroomList2.get(0).getChatroomId());
        Assertions.assertThat(chatroom.get().getUser1()).isEqualTo(chatroomList2.get(0).getUser1());
    }

    @Transactional
    @Test
    void delete() {

        List<Chatroom> chatroomList1 = dbChatroomRepository.findByUser("tt");
        Assertions.assertThat(chatroomList1.size() == 1);

        dbChatroomRepository.delete(chatroomList1.get(0).getChatroomId());
        chatroomList1 = dbChatroomRepository.findByUser("tt");
        Assertions.assertThat(chatroomList1.size() == 0);
    }

    @Transactional
    @Test
    void updateLastMessage() {
        Message message = new Message();
        message.setChatroomId(chatroom_id);
        message.setSenderId("tt");
        message.setSendTime(new Timestamp(System.currentTimeMillis()));
        message.setRendezvousFlag(false);
        message.setContent("1");
        Long message_id = dbMessageRepository.save(message).getMessageId();
        dbChatroomRepository.updateLastMessage(chatroom_id,message_id);
        List<Chatroom> chatroomList = dbChatroomRepository.findByUser("tt");
        Assertions.assertThat(chatroomList.get(0).getLastMessageId()).isEqualTo(message_id);

    }

}
