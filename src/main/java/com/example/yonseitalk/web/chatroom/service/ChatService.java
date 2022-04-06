package com.example.yonseitalk.web.chatroom.service;

import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.chatroom.domain.ChatroomQdslRepository;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDto;
import com.example.yonseitalk.web.chatroom.util.RendezvousMasker;
import com.example.yonseitalk.web.message.domain.MessageRepository;
import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.message.domain.Message;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.domain.AccountRepository;
import com.example.yonseitalk.web.account.service.AccountService;
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
    public Long getMessageCount(Long chatroom_id){
        if(chatroom_id<0) return 0L;
        return messageRepository.countMessages(chatroom_id);
    }

    @Transactional
    public Long addChatroom(String user_1_id, String user_2_id) {

        Optional<Chatroom> chatroomCheck = chatroomRepository.findByPairUser(user_1_id, user_2_id);
        if(chatroomCheck.isPresent()) return chatroomCheck.get().getChatroomId();

        Chatroom chatroom = Chatroom.builder()
                .user1(accountRepository.findById(user_1_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_1_id)))
                .user2(accountRepository.findById(user_2_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_2_id)))
                .build();

        chatroom = chatroomRepository.save(chatroom);
        return chatroom.getChatroomId();
    }

    @Transactional
    public MessageDto.MessageList messageInquiry(Long chatroom_id, String user_id) {

        AccountDto accountDto = accountService.findById(user_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_id));
        List<Message> messageList = messageRepository.findByChatroomId(chatroom_id);

        messageList.forEach(message -> {
            //가려진 메시지도 읽었다고 처리했다 가정하자.
            if(message.getReadTime()==null && !message.getSender().getAccountId().equals(user_id)){
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
    public Long sendMessage(String user_id, Long chatroom_id, String content, Long rendezvous_time) {
        Chatroom chatroom = chatroomRepository.findByChatroomId(chatroom_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+chatroom_id));;
        Account user = accountRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_id));;

        Message message = Message.builder()
                .sender(user)
                .chatroom(chatroom)
                .content(content)
                .sendTime(new Timestamp(System.currentTimeMillis()))
                .rendezvousFlag(false) // default는 기본 메시지
                .build();

        if (!rendezvous_time.equals(-1L) && rendezvous_time != null){
            message.setRendezvousFlag(true);
            message.setRendezvousTime(new Timestamp(message.getSendTime().getTime() + (rendezvous_time * 60000L)));  // 60000 ms = 1 min
            message.setRendezvousLocation(user.getAccountLocation());
        }

        chatroom.addMessage(message);
        return messageRepository.save(message).getMessageId();
    }

    @Transactional
    public ChatroomDto.ChatroomList createChatroomView(String userId){
        AccountDto account = accountService.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id: " + userId));
        List<ChatroomDto.ChatroomDetail> chatroomDetailList = chatroomQdslRepository.findChatroomListbyUser(userId);
        chatroomDetailList.forEach(chatroomDetail -> chatroomDetail.setContent(RendezvousMasker.maskRendezvous(chatroomDetail, account)));
        return ChatroomDto.ChatroomList.fromChatroomDetailList(chatroomDetailList, userId);
    }

}
