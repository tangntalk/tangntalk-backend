package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDetail;
import com.example.yonseitalk.web.chatroom.service.ChatService;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.user.domain.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DBChatroomRepositoryTest {


    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatroomRepository chatroomRepository;

    Long chatroom_id;

    @BeforeEach
    void setup(){

        UserDto user1 = UserDto.builder()
                .userId("t1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user2 = UserDto.builder()
                .userId("t2")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        UserDto user3 = UserDto.builder()
                .userId("t3")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        Long id = chatService.addChatroom(user1.getUserId(), user2.getUserId());
        Long id2 = chatService.addChatroom(user2.getUserId(), user3.getUserId());
        chatService.sendMessage("t1", id, "aaa", -1L);
        chatService.sendMessage("t2", id2, "aaa", -1L);

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
                .userId("t1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();

        User user2 = User.builder()
                .userId("t2")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .userLocation("공학관")
                .connectionStatus(true)
                .build();


//        Chatroom chatroom = new Chatroom();
//        chatroom.setChatroomId(null);
//        chatroom.setUser1("t1");
//        chatroom.setUser2("t2");
//        chatroom_id = chatService.save(chatroom).getChatroomId();
        Long id = chatService.addChatroom(user1.getUserId(), user2.getUserId());

        chatService.sendMessage("t1", id, "aaa", -1L);
        List<ChatroomDetail> chatroom1 =  chatService.findChatroom(user1.getUserId());
        Assertions.assertThat(chatroom1.get(0).getUser1()).isEqualTo("t1");

    }

    @Transactional
    @Test
    void findByUserandfindById(){

        List<ChatroomDetail> chatroomList1 = chatService.findChatroom("t1");
        List<ChatroomDetail> chatroomList2 = chatService.findChatroom("t2");
        Assertions.assertThat(chatroomList1.size()).isEqualTo(1);
        Assertions.assertThat(chatroomList2.size()).isEqualTo(2);
        Optional<Chatroom> chatroom = chatroomRepository.findByChatroomId(chatroomList1.get(0).getChatroomId());

        Assertions.assertThat(chatroom.isPresent());
        Assertions.assertThat(chatroomList1.get(0).getChatroomId()).isEqualTo(chatroomList2.get(0).getChatroomId());
        Assertions.assertThat(chatroom.get().getUser1().getUserId()).isEqualTo(chatroomList1.get(0).getUser1());
    }

    @Transactional
    @Test
    void delete() {
        List<ChatroomDetail> chatroomList1 = chatService.findChatroom("t1");
        Assertions.assertThat(chatroomList1.size()).isEqualTo(1);
        chatroomRepository.deleteChatroomByChatroomId(chatroomList1.get(0).getChatroomId());
        chatroomList1 = chatService.findChatroom("t1");
        Assertions.assertThat(chatroomList1.size() == 0);
    }

    @Transactional
    @Test
    void updateLastMessage() {

        List<ChatroomDetail> chatroomList = chatService.findChatroom("t1");
        Long message_id = chatService.sendMessage("t1", chatroomList.get(0).getChatroomId(), "aa", -1L);
//        Message message = new Message();
//        message.setChatroomId(chatroom_id);
//        message.setSenderId("t1");
//        message.setSendTime(new Timestamp(System.currentTimeMillis()));
//        message.setRendezvousFlag(false);
//        message.setContent("1");
//        Long message_id = dbMessageRepository.save(message).getMessageId();
//        dbChatroomRepository.updateLastMessage(chatroom_id,message_id);
        chatroomList = chatService.findChatroom("t1");
        System.out.println(chatroomList);
        Assertions.assertThat(chatroomList.get(0).getSenderId()).isEqualTo("t1");

    }

    @Transactional
    @Test
    void messageInquiry(){
        List<ChatroomDetail> chatroomList = chatService.findChatroom("t1");
        Assertions.assertThat(chatroomList.size()).isEqualTo(1);
        List<MessageDto> MessageList = chatService.messageInquiry(chatroomList.get(0).getChatroomId(), "t1");
        System.out.println(MessageList);
    }

}
