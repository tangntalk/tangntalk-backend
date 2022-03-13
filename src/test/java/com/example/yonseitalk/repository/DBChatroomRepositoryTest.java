package com.example.yonseitalk.repository;


import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.chatroom.domain.ChatroomQdslRepository;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDto;
import com.example.yonseitalk.web.chatroom.service.ChatService;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.service.AccountService;
import com.example.yonseitalk.web.message.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class DBChatroomRepositoryTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ChatroomQdslRepository chatroomQdslRepository;

    Long chatroom_id1;
    Long chatroom_id2;
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
            .name("jihoon2")
            .password("ddda")
            .statusMessage("hihi")
            .type("일반")
            .accountLocation("공학관")
            .connectionStatus(false)
            .build();

    AccountDto user3 = AccountDto.builder()
            .accountId("t3")
            .name("jihoon3")
            .password("ddda")
            .statusMessage("hihi")
            .type("일반")
            .accountLocation("공학관")
            .connectionStatus(true)
            .build();

    @BeforeEach
    void setup(){

        accountService.save(user1);
        accountService.save(user2);
        accountService.save(user3);
        chatroom_id1 = chatService.addChatroom(user1.getAccountId(), user2.getAccountId());
        chatroom_id2 = chatService.addChatroom(user2.getAccountId(), user3.getAccountId());
        chatService.sendMessage(user1.getAccountId(), chatroom_id1, "I am user 1", -1L);
        chatService.sendMessage(user2.getAccountId(), chatroom_id2, "I am user 2", -1L);

    }

    @Transactional
    @Test
    void 채팅방_저장(){
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroom_id1);
        Assertions.assertThat(chatroom.isPresent());
    }

    @Transactional
    @Test
    void 채팅방_vanity_check(){

        List<ChatroomDto.SingleChatroom> chatroomList1 = chatService.createChatroomView("t1");
        List<ChatroomDto.SingleChatroom> chatroomList2 = chatService.createChatroomView("t2");
        Assertions.assertThat(chatroomList1.size()).isEqualTo(1);
        Assertions.assertThat(chatroomList2.size()).isEqualTo(2);
        Optional<Chatroom> chatroom = chatroomRepository.findByChatroomId(chatroomList1.get(0).getChatroomId());

        Assertions.assertThat(chatroom.isPresent());
        Assertions.assertThat(chatroomList1.get(0).getChatroomId()).isEqualTo(chatroomList2.get(0).getChatroomId());
        Assertions.assertThat(chatroom.get().getUser2().getAccountId()).isEqualTo(chatroomList1.get(0).getOpponentId());
    }

    @Transactional
    @Test
    void 메시지_보내고_조회하기(){
        Long start = System.currentTimeMillis();
        List<ChatroomDto.SingleChatroom> chatroomList = chatService.createChatroomView("t1");
        log.info("small chatroom took {}", System.currentTimeMillis() - start);
        Assertions.assertThat(chatroomList.size()).isEqualTo(1);

        int numMessages = 1000;
        start = System.currentTimeMillis();
        for(int i =0;i < numMessages; i++){
            chatService.sendMessage(user2.getAccountId(), chatroom_id1, "this is message "+i, -1L);
        }
        log.info("insert took {}", System.currentTimeMillis() - start);


        start = System.currentTimeMillis();
        List<MessageDto.SingleMessage> messageList = chatService.messageInquiry(chatroomList.get(0).getChatroomId(), "t1");
        log.info("long chatroom took {}", System.currentTimeMillis()- start);
        Assertions.assertThat(messageList.size() == numMessages+1);
    }


}
