package com.example.tangntalk.repository;


import com.example.tangntalk.web.chatroom.domain.Chatroom;
import com.example.tangntalk.web.chatroom.dto.response.ChatroomListDto;
import com.example.tangntalk.web.chatroom.repository.ChatroomQdslRepository;
import com.example.tangntalk.web.chatroom.repository.ChatroomRepository;
import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import com.example.tangntalk.web.message.dto.MessageDto;
import com.example.tangntalk.web.message.dto.response.MessageListDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    Long chatroomId1;
    Long chatroomId2;

    AccountDto user1 = AccountDto.builder()
            .username("t1")
            .name("jihoon")
            .password("ddda")
            .statusMessage("hihi")
            .type("학생")
            .accountLocation("공학관")
            .connectionStatus(true)
            .build();

    AccountDto user2 = AccountDto.builder()
            .username("t2")
            .name("jihoon2")
            .password("ddda")
            .statusMessage("hihi")
            .type("일반")
            .accountLocation("공학관")
            .connectionStatus(false)
            .build();

    AccountDto user3 = AccountDto.builder()
            .username("t3")
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
        chatroomId1 = chatService.addChatroom(user1.getUsername(), user2.getUsername());
        chatroomId2 = chatService.addChatroom(user2.getUsername(), user3.getUsername());
        chatService.sendMessage(user1.getUsername(), chatroomId1, "I am user 1", -1L);
        chatService.sendMessage(user2.getUsername(), chatroomId2, "I am user 2", -1L);

    }

    @Transactional
    @Test
    void 채팅방_저장(){
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroomId1);
        Assertions.assertThat(chatroom.isPresent());
    }

    @Transactional
    @Test
    void 채팅방_vanity_check(){

        ChatroomListDto chatroomList1 = chatService.createChatroomView("t1");
        ChatroomListDto chatroomList2 = chatService.createChatroomView("t2");
        Assertions.assertThat(chatroomList1.getChatrooms().size()).isEqualTo(1);
        Assertions.assertThat(chatroomList2.getChatrooms().size()).isEqualTo(2);
        Optional<Chatroom> chatroom = chatroomRepository.findByChatroomId(chatroomList1.getChatrooms().get(0).getChatroomId());

        Assertions.assertThat(chatroom.isPresent());
        Assertions.assertThat(chatroomList1.getChatrooms().get(0).getChatroomId()).isEqualTo(chatroomList2.getChatrooms().get(0).getChatroomId());
        Assertions.assertThat(chatroom.get().getUser2().getUsername()).isEqualTo(chatroomList1.getChatrooms().get(0).getOpponentId());
    }

    @Transactional
    @Test
    void 메시지_보내고_조회하기(){
        Long start = System.currentTimeMillis();
        ChatroomListDto chatroomList = chatService.createChatroomView("t1");
        log.info("small chatroom took {}", System.currentTimeMillis() - start);
        Assertions.assertThat(chatroomList.getChatrooms().size()).isEqualTo(1);

        int numMessages = 1000;
        start = System.currentTimeMillis();
        for(int i =0;i < numMessages; i++){
            chatService.sendMessage(user2.getUsername(), chatroomId1, "this is message "+i, -1L);
        }
        log.info("insert took {}", System.currentTimeMillis() - start);


        start = System.currentTimeMillis();
        MessageListDto messageList = chatService.messageInquiry(chatroomList.getChatrooms().get(0).getChatroomId(), "t1");
        log.info("long chatroom took {}", System.currentTimeMillis()- start);
        Assertions.assertThat(messageList.getMessageList().size() == numMessages+1);
    }


}
