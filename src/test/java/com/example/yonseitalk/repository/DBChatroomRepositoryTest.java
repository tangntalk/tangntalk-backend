package com.example.yonseitalk.repository;

import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDetail;
import com.example.yonseitalk.web.chatroom.service.ChatService;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
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
    private AccountService accountService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatroomRepository chatroomRepository;

    Long chatroom_id;

    @BeforeEach
    void setup(){

        AccountDto user1 = AccountDto.builder()
                .accountId("t1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user2 = AccountDto.builder()
                .accountId("t2")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        AccountDto user3 = AccountDto.builder()
                .accountId("t3")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        accountService.save(user1);
        accountService.save(user2);
        accountService.save(user3);
        Long id = chatService.addChatroom(user1.getAccountId(), user2.getAccountId());
        Long id2 = chatService.addChatroom(user2.getAccountId(), user3.getAccountId());
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

        Account user1 = Account.builder()
                .accountId("t1")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("학생")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();

        Account user2 = Account.builder()
                .accountId("t2")
                .name("jihoon")
                .password("ddda")
                .statusMessage("hihi")
                .type("일반")
                .accountLocation("공학관")
                .connectionStatus(true)
                .build();


//        Chatroom chatroom = new Chatroom();
//        chatroom.setChatroomId(null);
//        chatroom.setUser1("t1");
//        chatroom.setUser2("t2");
//        chatroom_id = chatService.save(chatroom).getChatroomId();
        Long id = chatService.addChatroom(user1.getAccountId(), user2.getAccountId());

        chatService.sendMessage("t1", id, "aaa", -1L);
        List<ChatroomDetail> chatroom1 =  chatService.findChatroom(user1.getAccountId());
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
        Assertions.assertThat(chatroom.get().getUser1().getAccountId()).isEqualTo(chatroomList1.get(0).getUser1());
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
