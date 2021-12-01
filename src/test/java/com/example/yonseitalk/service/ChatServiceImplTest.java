//package com.example.yonseitalk.service;
//
//import com.example.yonseitalk.domain.Chatroom;
//import com.example.yonseitalk.domain.ChatroomWrapper;
//import com.example.yonseitalk.domain.Message;
//import com.example.yonseitalk.domain.User;
//import com.example.yonseitalk.repository.ChatroomRepository;
//import com.example.yonseitalk.repository.MessageRepository;
//import com.example.yonseitalk.repository.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class ChatServiceImplTest {
//    @Autowired
//    private ChatService chatService;
//
//    @Autowired
//    private ChatroomRepository chatroomRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    Long chatroom_id;
//
//    @BeforeEach
//    void setUp(){
//        User user1 = new User();
//        user1.setUser_id("flaxinger1");
//        user1.setName("yohanmok1");
//        user1.setPassword("aaa");
//        user1.setStatus_message("Talk");
//        user1.setType("학생");
//        user1.setUser_location("공학관");
//        user1.setConnection_status(true);
//        userRepository.save(user1);
//
//        User user2 = new User();
//        user2.setUser_id("flaxinger2");
//        user2.setName("yohanmok2");
//        user2.setPassword("aaa");
//        user2.setStatus_message("Talk");
//        user2.setType("학생");
//        user2.setUser_location("공학관");
//        user2.setConnection_status(true);
//        userRepository.save(user2);
//
//        User user3 = new User();
//        user3.setUser_id("flaxinger3");
//        user3.setName("yohanmok3");
//        user3.setPassword("aaa");
//        user3.setStatus_message("Talk");
//        user3.setType("학생");
//        user3.setUser_location("공학관");
//        user3.setConnection_status(false);
//        userRepository.save(user3);
//
//    }
//
//
//    @Test
//    void addChatroomTest(){
//        chatroom_id = chatService.addChatroom("flaxinger1","flaxinger2");
//        assertThat(chatroomRepository.findById(chatroom_id)).isPresent();
//    }
//
//    @Test
//    void findChatroomTest1(){
//        //given
//        chatroom_id =chatService.addChatroom("flaxinger1","flaxinger2");
//        chatService.addChatroom("flaxinger1","flaxinger3");
//
//        //when
//        List<ChatroomWrapper> chatroomWrapperList = chatService.findChatroom("flaxinger1");
//
//        //then
//        assertThat(chatroomWrapperList.size()).isEqualTo(0);
//
//
//
//
//    }
//    @Test
//    void findChatroomTest2(){
//        //given
//        chatroom_id =chatService.addChatroom("flaxinger1","flaxinger2");
//        chatService.addChatroom("flaxinger1","flaxinger3");
//        Long message_id = chatService.sendMessage("flaxinger1",chatroom_id,"hello",null,null);
//        //when
//        List<ChatroomWrapper> chatroomWrapperList = chatService.findChatroom("flaxinger1");
//        //then
//        assertThat(chatroomWrapperList.size()).isEqualTo(1);
//        assertThat(chatroomWrapperList.get(0).getLast_message().getContent()).isEqualTo("hello");
//        assertThat(messageRepository.findById(message_id).get().getContent()).isNotEqualTo("hello");
//    }
//    @Test
//    void findChatroomTest3() throws InterruptedException {
//        //given
//        chatroom_id = chatService.addChatroom("flaxinger1","flaxinger2");
//        Long chatroom_id2 = chatService.addChatroom("flaxinger1","flaxinger3");
//        userRepository.updateUserConnectionStatus("flaxinger3",true);
//        chatService.sendMessage("flaxinger1",chatroom_id2,"hello",null,null);
//
//        TimeUnit.MILLISECONDS.sleep(20);
//        chatService.sendMessage("flaxinger1",chatroom_id,"hello2",null,null);
//
//        //when
//        List<ChatroomWrapper> chatroomWrapperList = chatService.findChatroom("flaxinger1");
//        //then
//        assertThat(chatroomWrapperList.size()).isEqualTo(2);
//        assertThat(chatroomWrapperList.get(0).getLast_message().getContent()).isEqualTo("hello2");
//    }
//
//    @Test
//    void sendMessageTest(){
//        chatroom_id =chatService.addChatroom("flaxinger1","flaxinger2");
//        Long chatroom_id2 = chatService.addChatroom("flaxinger1","flaxinger3");
//
//        Long message_id = chatService.sendMessage("flaxinger1",chatroom_id,"hello",null,null);
//        assertThat(messageRepository.findByChatroomId(chatroom_id).size()).isEqualTo(1);
//
//        assertThat(chatroomRepository.findById(chatroom_id).get().getLast_message_id()).isEqualTo(message_id);
//
//        chatService.sendMessage("flaxinger1",chatroom_id2,"hello",null,null);
//
//        assertThat(messageRepository.findByChatroomId(chatroom_id2).size()).isEqualTo(0);
//
//    }
//
//    @Test
//    void messageInquiryTest() throws InterruptedException {
//        chatroom_id = chatService.addChatroom("flaxinger1","flaxinger2");
//        chatService.sendMessage("flaxinger1",chatroom_id,"hello",null,null);
//        chatService.sendMessage("flaxinger1",chatroom_id,"hello2","백양관",new Timestamp(System.currentTimeMillis()));
//        chatService.sendMessage("flaxinger1",chatroom_id,"hello3","공학관", Timestamp.valueOf("2029-03-20 10:20:30.0"));
//        TimeUnit.MILLISECONDS.sleep(20);
//
//        List<Message> messageList = chatService.messageInquiry(chatroom_id, "flaxinger2");
//        assertThat(messageList.size()).isEqualTo(3);
//        assertThat(messageList.get(0).getContent()).isEqualTo("hello");
//        assertThat(messageList.get(1).getContent()).isEqualTo("hidden message");
//        assertThat(messageList.get(2).getContent()).isEqualTo("hello3");
//        assertThat(messageList.get(2).getRendezvous_flag()).isTrue();
//        assertThat(messageList.get(1).getRead_time()).isNotNull();
//
//
//    }
//
//
//
//}