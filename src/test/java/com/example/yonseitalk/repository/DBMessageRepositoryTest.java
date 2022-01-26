//package com.example.yonseitalk.repository;
//
//import com.example.yonseitalk.web.chatroom.dao.Chatroom;
//import com.example.yonseitalk.web.chatroom.dao.ChatroomRepository;
//import com.example.yonseitalk.web.message.dao.Message;
//import com.example.yonseitalk.web.message.dao.MessageRepository;
//import com.example.yonseitalk.web.user.dao.User;
//import com.example.yonseitalk.web.user.dto.UserDto;
//import com.example.yonseitalk.web.user.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class DBMessageRepositoryTest {
//    @Autowired
//    private ChatroomRepository chatroomRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    Long chatroom_id;
//
//
//    @BeforeEach
//    void setUp(){
//
//        UserDto user1 = UserDto.builder()
//                .userId("flaxinger1")
//                .name("yohanmok1")
//                .password("aaa")
//                .statusMessage("Talk")
//                .type("학생")
//                .userLocation("공학관")
//                .connectionStatus(true)
//                .build();
//
//        UserDto user2 = UserDto.builder()
//                .userId("flaxinger2")
//                .name("yohanmok2")
//                .password("aaa")
//                .statusMessage("Talk")
//                .type("학생")
//                .userLocation("공학관")
//                .connectionStatus(false)
//                .build();
//
//
//        userService.save(user1);
//        userService.save(user2);
//
//        Chatroom chatroom = new Chatroom();
//        chatroom.setChatroomId(null);
//        chatroom.setUser1("flaxinger1");
//        chatroom.setUser2("flaxinger2");
//        chatroom_id = chatroomRepository.save(chatroom).getChatroomId();
//    }
//
//    @Test
//    void addMessage(){
//        Message message = new Message();
//        message.setChatroomId(chatroom_id);
//        message.setSenderId("flaxinger1");
////        message.setReceiver_id("flaxinger2");
//        message.setSendTime(new Timestamp(System.currentTimeMillis()));
//        message.setRendezvousFlag(false);
//        message.setContent("content");
//
//        message = messageRepository.save(message);
//
//        Message findMessage = messageRepository.findById(message.getMessageId()).orElseThrow();
//        assertThat(message.getMessageId()).isEqualTo(findMessage.getMessageId());
//        assertThat(message.getContent()).isEqualTo(findMessage.getContent());
//    }
//    @Test
//    void findByChatroomIdTest(){
//        Message message = new Message();
//        message.setChatroomId(chatroom_id);
//        message.setSenderId("flaxinger1");
////        message.setReceiver_id("flaxinger2");
//        message.setSendTime(new Timestamp(System.currentTimeMillis()));
//        message.setRendezvousFlag(false);
//        message.setContent("1");
//
//        Message message2 = new Message();
//        message2.setChatroomId(chatroom_id);
//        message2.setSenderId("flaxinger1");
////        message2.setReceiver_id("flaxinger2");
//        message2.setSendTime(new Timestamp(System.currentTimeMillis()));
//        message2.setRendezvousFlag(false);
//        message2.setContent("2");
//
//        Message message3 = new Message();
//        message3.setChatroomId(chatroom_id);
//        message3.setSenderId("flaxinger1");
////        message3.setReceiver_id("flaxinger2");
//        message3.setSendTime(new Timestamp(System.currentTimeMillis()));
//        message3.setRendezvousFlag(false);
//        message3.setContent("3");
//
//        messageRepository.save(message);
//        messageRepository.save(message2);
//        messageRepository.save(message3);
//
//        List<Message> messageList = messageRepository.findByChatroomId(chatroom_id);
//        assertThat(messageList.size()).isEqualTo(3);
//
//    }
//    @Test
//    void updateReadTimeTest(){
//        Message message = new Message();
//        message.setChatroomId(chatroom_id);
//        message.setSenderId("flaxinger1");
////        message.setReceiver_id("flaxinger2");
//        message.setSendTime(new Timestamp(System.currentTimeMillis()));
//        message.setRendezvousFlag(false);
//        message.setContent("1");
//
//        Long message_id = messageRepository.save(message).getMessageId();
//
//        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//        currentTime.setNanos(0);
//        messageRepository.updateReadTime(message_id,currentTime);
//
//
//        Message findMessage = messageRepository.findById(message_id).orElseThrow();
//        assertThat(findMessage.getReadTime()).isEqualTo(currentTime);
//
//    }
//
//}