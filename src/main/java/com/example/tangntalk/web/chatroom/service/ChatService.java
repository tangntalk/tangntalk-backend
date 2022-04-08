package com.example.tangntalk.web.chatroom.service;

import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.chatroom.domain.ChatroomQdslRepository;
import com.example.tangntalk.web.chatroom.domain.ChatroomRepository;
import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import com.example.tangntalk.web.chatroom.util.RendezvousMasker;
import com.example.tangntalk.web.message.domain.MessageRepository;
import com.example.tangntalk.web.chatroom.domain.Chatroom;
import com.example.tangntalk.web.message.domain.Message;
import com.example.tangntalk.web.message.dto.MessageDto;
import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.domain.AccountRepository;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ChatroomQdslRepository chatroomQdslRepository;

    @Transactional
    public Long getMessageCount(Long chatroomId){
        if(chatroomId<0) return 0L;
        return messageRepository.countMessages(chatroomId);
    }

    @Transactional
    public Long addChatroom(String user_1Id, String user_2Id) {

        Account user1 = accountRepository.findAccountByUsername(user_1Id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_1Id));
        Account user2 = accountRepository.findAccountByUsername(user_2Id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_2Id));

        Optional<Chatroom> chatroomCheck = chatroomRepository.findChatroomByUserPair(user1.getId(), user2.getId());
        if(chatroomCheck.isPresent()) return chatroomCheck.get().getChatroomId();

        Chatroom chatroom = Chatroom.builder()
                .user1(user1)
                .user2(user2)
                .build();

        chatroom = chatroomRepository.save(chatroom);
        return chatroom.getChatroomId();
    }

    @Transactional
    public MessageDto.MessageList messageInquiry(Long chatroomId, String userId) {

        AccountDto accountDto = accountService.findByUsername(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        List<Message> messageList = messageRepository.findByChatroomId(chatroomId);

        messageList.forEach(message -> {
            //가려진 메시지도 읽었다고 처리했다 가정하자.
            if(message.getReadTime()==null && !message.getSender().getUsername().equals(userId)){
                log.info(message.getSender() + " should not equal " + userId);
                message.setReadTime(new Timestamp(System.currentTimeMillis()));
            }
        });

        List<MessageDto> messageDtos = messageList.stream().map(MessageDto::fromMessage).map(messageDto -> {
            messageDto.setContent(RendezvousMasker.maskRendezvous(messageDto, accountDto));
            return messageDto;
        }).collect(Collectors.toList());

        return MessageDto.MessageList.fromMessageDtoList(messageDtos);
    }



    @Transactional
    public Long sendMessage(String userId, Long chatroomId, String content, Long rendezvousTime) {
        Chatroom chatroom = chatroomRepository.findByChatroomId(chatroomId).orElseThrow(() -> new IllegalArgumentException("No user with id "+chatroomId));;
        Account user = accountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));;

        Message message = Message.builder()
                .sender(user)
                .chatroom(chatroom)
                .content(content)
                .sendTime(new Timestamp(System.currentTimeMillis()))
                .rendezvousFlag(false) // default는 기본 메시지
                .readTime(null)
                .build();

        if (rendezvousTime != null && !rendezvousTime.equals(-1L)){
            message.setRendezvousFlag(true);
            message.setRendezvousTime(new Timestamp(message.getSendTime().getTime() + (rendezvousTime * 60000L)));  // 60000 ms = 1 min
            message.setRendezvousLocation(user.getAccountLocation());
        }

        chatroom.addMessage(message);
        return messageRepository.save(message).getMessageId();
    }

    @Transactional
    public ChatroomDto.ChatroomList createChatroomView(String userId){
        AccountDto account = accountService.findByUsername(userId).orElseThrow(() -> new IllegalArgumentException("No user with id: " + userId));
        List<ChatroomDto.ChatroomDetail> chatroomDetailList = chatroomQdslRepository.findChatroomListbyUser(userId);
        chatroomDetailList.forEach(chatroomDetail -> chatroomDetail.setContent(RendezvousMasker.maskRendezvous(chatroomDetail, account)));
        return ChatroomDto.ChatroomList.fromChatroomDetailList(chatroomDetailList, userId);
    }

}
