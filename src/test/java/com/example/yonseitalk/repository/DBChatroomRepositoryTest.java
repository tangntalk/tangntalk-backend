package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;
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

    @BeforeEach
    void setup(){
        User user1 = new User();
        user1.setUser_id("flaxinger1");
        user1.setName("yohanmok1");
        user1.setPassword("aaa");
        user1.setStatus_message("Talk");
        user1.setType("1");
        user1.setUser_location("공학관");
        user1.setConnection_status(false);
        dbUserRepository.save(user1);

        User user2 = new User();
        user2.setUser_id("flaxinger2");
        user2.setName("yohanmok2");
        user2.setPassword("aaa");
        user2.setStatus_message("Talk");
        user2.setType("2");
        user2.setUser_location("공학관");
        user2.setConnection_status(false);
        dbUserRepository.save(user2);

        Chatroom chatroom = new Chatroom();
        chatroom.setChatroom_id(null);
        chatroom.setTalk("HaLo!");
        chatroom.setUser_1("flaxinger1");
        chatroom.setUser_2("flaxinger2");
        dbChatroomRepository.save(chatroom);
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

}
