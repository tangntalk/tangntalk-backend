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
    private DBUserRepository dbUserRepository;

    @Autowired
    private DBMessageRepository dbMessageRepository;

    Long chatroom_id;

    @BeforeEach
    void setup(){
        User user1 = new User();
        user1.setUser_id("flaxinger1");
        user1.setName("yohanmok1");
        user1.setPassword("aaa");
        user1.setStatus_message("Talk");
        user1.setType("학생");
        user1.setUser_location("공학관");
        user1.setConnection_status(false);
        dbUserRepository.save(user1);

        User user2 = new User();
        user2.setUser_id("flaxinger2");
        user2.setName("yohanmok2");
        user2.setPassword("aaa");
        user2.setStatus_message("Talk");
        user2.setType("학생");
        user2.setUser_location("공학관");
        user2.setConnection_status(false);
        dbUserRepository.save(user2);

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

        User user1 = new User();
        user1.setUser_id("flaxinger3");
        user1.setName("yohanmok1");
        user1.setPassword("aaa");
        user1.setStatus_message("Talk");
        user1.setType("학생");
        user1.setUser_location("공학관");
        user1.setConnection_status(false);
        dbUserRepository.save(user1);

        User user2 = new User();
        user2.setUser_id("flaxinger4");
        user2.setName("yohanmok2");
        user2.setPassword("aaa");
        user2.setStatus_message("Talk");
        user2.setType("학생");
        user2.setUser_location("공학관");
        user2.setConnection_status(false);
        dbUserRepository.save(user2);

        Chatroom chatroom = new Chatroom();
        chatroom.setChatroom_id(null);
        chatroom.setUser_1("flaxinger3");
        chatroom.setUser_2("flaxinger4");
        chatroom_id = dbChatroomRepository.save(chatroom).getChatroom_id();

        Optional<Chatroom> chatroom1 =dbChatroomRepository.findById(chatroom_id);
        Assertions.assertThat(chatroom1.get().getUser_1()).isEqualTo("flaxinger3");



    }

    @Transactional
    @Test
    void findByUserandfindById(){

        List<Chatroom> chatroomList1 = dbChatroomRepository.findByUser("flaxinger1");
        List<Chatroom> chatroomList2 = dbChatroomRepository.findByUser("flaxinger2");
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

        List<Chatroom> chatroomList1 = dbChatroomRepository.findByUser("flaxinger1");
        Assertions.assertThat(chatroomList1.size() == 1);

        dbChatroomRepository.delete(chatroomList1.get(0).getChatroom_id());
        chatroomList1 = dbChatroomRepository.findByUser("flaxinger1");
        Assertions.assertThat(chatroomList1.size() == 0);
    }

    @Transactional
    @Test
    void updateLastMessage() {
        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id("flaxinger1");
//        message.setReceiver_id("flaxinger2");
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        message.setRendezvous_flag(false);
        message.setContent("1");
        Long message_id = dbMessageRepository.save(message).getMessage_id();
        dbChatroomRepository.updateLastMessage(chatroom_id,message_id);
        List<Chatroom> chatroomList = dbChatroomRepository.findByUser("flaxinger1");
        Assertions.assertThat(chatroomList.get(0).getLast_message_id()).isEqualTo(message_id);

    }

}
