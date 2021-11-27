package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DBMessageRepositoryTest {
    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    Long chatroom_id;


    @BeforeEach
    void setUp(){
        User user1 = new User();
        user1.setUser_id("flaxinger1");
        user1.setName("yohanmok1");
        user1.setPassword("aaa");
        user1.setStatus_message("Talk");
        user1.setType("학생");
        user1.setUser_location("공학관");
        user1.setConnection_status(false);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUser_id("flaxinger2");
        user2.setName("yohanmok2");
        user2.setPassword("aaa");
        user2.setStatus_message("Talk");
        user2.setType("학생");
        user2.setUser_location("공학관");
        user2.setConnection_status(false);
        userRepository.save(user2);

        Chatroom chatroom = new Chatroom();
        chatroom.setChatroom_id(null);
        chatroom.setUser_1("flaxinger1");
        chatroom.setUser_2("flaxinger2");
        chatroom_id = chatroomRepository.save(chatroom).getChatroom_id();
    }

    @Test
    void addMessage(){
        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id("flaxinger1");
//        message.setReceiver_id("flaxinger2");
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        message.setRendezvous_flag(false);
        message.setContent("content");

        message = messageRepository.save(message);

        Message findMessage = messageRepository.findById(message.getMessage_id()).orElseThrow();
        assertThat(message.getMessage_id()).isEqualTo(findMessage.getMessage_id());
        assertThat(message.getContent()).isEqualTo(findMessage.getContent());
    }
    @Test
    void findByChatroomIdTest(){
        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id("flaxinger1");
//        message.setReceiver_id("flaxinger2");
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        message.setRendezvous_flag(false);
        message.setContent("1");

        Message message2 = new Message();
        message2.setChatroom_id(chatroom_id);
        message2.setSender_id("flaxinger1");
//        message2.setReceiver_id("flaxinger2");
        message2.setSend_time(new Timestamp(System.currentTimeMillis()));
        message2.setRendezvous_flag(false);
        message2.setContent("2");

        Message message3 = new Message();
        message3.setChatroom_id(chatroom_id);
        message3.setSender_id("flaxinger1");
//        message3.setReceiver_id("flaxinger2");
        message3.setSend_time(new Timestamp(System.currentTimeMillis()));
        message3.setRendezvous_flag(false);
        message3.setContent("3");

        messageRepository.save(message);
        messageRepository.save(message2);
        messageRepository.save(message3);

        List<Message> messageList = messageRepository.findByChatroomId(chatroom_id);
        assertThat(messageList.size()).isEqualTo(3);

    }
    @Test
    void updateReadTimeTest(){
        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id("flaxinger1");
//        message.setReceiver_id("flaxinger2");
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        message.setRendezvous_flag(false);
        message.setContent("1");

        Long message_id = messageRepository.save(message).getMessage_id();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        currentTime.setNanos(0);
        messageRepository.updateReadTime(message_id,currentTime);


        Message findMessage = messageRepository.findById(message_id).orElseThrow();
        assertThat(findMessage.getRead_time()).isEqualTo(currentTime);

    }

}