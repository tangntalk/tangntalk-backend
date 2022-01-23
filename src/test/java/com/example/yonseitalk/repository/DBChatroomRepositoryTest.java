package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.domain.User;
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
    private UserRepository userRepository;

    @Autowired
    private DBMessageRepository dbMessageRepository;

    Long chatroom_id;

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


        Chatroom chatroom = new Chatroom();
        chatroom.setChatroom_id(null);
        chatroom.setUser_1("flaxinger1");
        chatroom.setUser_2("flaxinger2");
        chatroom_id = dbChatroomRepository.save(chatroom).getChatroom_id();
    }

//    @Transactional
//    @Test
//    void addChatroom(){
//        Optional<Chatroom> chatroom = dbChatroomRepository.findById(4L);
//        Assertions.assertThat(chatroom.isPresent());
//        Assertions.assertThat(4L).isEqualTo(chatroom.get().getChatroom_id());
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
        chatroom.setChatroom_id(null);
        chatroom.setUser_1("tt");
        chatroom.setUser_2("nam");
        chatroom_id = dbChatroomRepository.save(chatroom).getChatroom_id();

        Optional<Chatroom> chatroom1 =dbChatroomRepository.findById(chatroom_id);
        Assertions.assertThat(chatroom1.get().getUser_1()).isEqualTo("tt");



    }

    @Transactional
    @Test
    void findByUserandfindById(){

        List<Chatroom> chatroomList1 = dbChatroomRepository.findByUser("tt");
        List<Chatroom> chatroomList2 = dbChatroomRepository.findByUser("nam");
        Assertions.assertThat(chatroomList1.size() == 1);
        Assertions.assertThat(chatroomList2.size() == 1);
        Optional<Chatroom> chatroom = dbChatroomRepository.findById(chatroomList1.get(0).getChatroom_id());

        Assertions.assertThat(chatroom.isPresent());
        Assertions.assertThat(chatroomList1.get(0).getChatroom_id()).isEqualTo(chatroomList2.get(0).getChatroom_id());
        Assertions.assertThat(chatroom.get().getUser_1()).isEqualTo(chatroomList2.get(0).getUser_1());
    }

    @Transactional
    @Test
    void delete() {

        List<Chatroom> chatroomList1 = dbChatroomRepository.findByUser("tt");
        Assertions.assertThat(chatroomList1.size() == 1);

        dbChatroomRepository.delete(chatroomList1.get(0).getChatroom_id());
        chatroomList1 = dbChatroomRepository.findByUser("tt");
        Assertions.assertThat(chatroomList1.size() == 0);
    }

    @Transactional
    @Test
    void updateLastMessage() {
        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id("tt");
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        message.setRendezvous_flag(false);
        message.setContent("1");
        Long message_id = dbMessageRepository.save(message).getMessage_id();
        dbChatroomRepository.updateLastMessage(chatroom_id,message_id);
        List<Chatroom> chatroomList = dbChatroomRepository.findByUser("tt");
        Assertions.assertThat(chatroomList.get(0).getLast_message_id()).isEqualTo(message_id);

    }

}
