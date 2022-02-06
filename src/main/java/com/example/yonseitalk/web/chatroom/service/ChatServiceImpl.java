package com.example.yonseitalk.web.chatroom.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.message.domain.MessageRepository;
import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDetail;
import com.example.yonseitalk.web.message.domain.Message;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.user.domain.User;
import com.example.yonseitalk.web.user.domain.UserRepository;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final UserService userService;

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
                .user1(userRepository.findById(user_1_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_1_id)))
                .user2(userRepository.findById(user_2_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_2_id)))
                .build();

        chatroom = chatroomRepository.save(chatroom);
        return chatroom.getChatroomId();
    }

    @Transactional
    public List<ChatroomDetail> findChatroom(String user_id) {

        UserDto userDto = userService.findById(user_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_id));

        List<ChatroomDetail> chatroomDetailList = chatroomRepository.findChatroomListbyUser(user_id).stream().map(ChatroomDetail::fromProjection).collect(Collectors.toList());
        chatroomDetailList.forEach(chatroomDetail -> chatroomDetail.setContent(transformContent(chatroomDetail, userDto)));
        return chatroomDetailList;
    }

    @Transactional
    public List<MessageDto> messageInquiry(Long chatroom_id, String user_id) {

        UserDto userDto = userService.findById(user_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_id));
        List<MessageDto> messageList = messageRepository.findByChatroomId(chatroom_id).stream().map(MessageDto::fromMessage).collect(Collectors.toList());

        messageList.forEach(message -> {
            message.setContent(transformContent(message,userDto));
            //가려진 메시지도 읽었다고 처리했다 가정하자.
            if(message.getReadTime()==null && !message.getSenderId().equals(user_id)){
                message.setReadTime(new Timestamp(System.currentTimeMillis()));
                Message updatedMessage = messageRepository.findMessageByMessageId(message.getMessageId());
                updatedMessage.setReadTime(message.getReadTime());
//                messageRepository.updateReadTime(message.getMessageId(),new Timestamp(System.currentTimeMillis()));
            }
        });
        return messageList;
    }


    @Transactional
    public String transformContent(MessageDto messageDto, UserDto userDto){

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        messageDto.setContent(AES128.getAES128_Decode(messageDto.getContent()));
        if(messageDto.getRendezvousFlag() && !messageDto.getSenderId().equals(userDto.getUserId())) {
            if (!messageDto.getRendezvousLocation().equals(userDto.getUserLocation()) || currentTime.after(messageDto.getRendezvousTime())) {
                messageDto.setContent("hidden message");
            }
        }
        return messageDto.getContent();
    }

    @Transactional
    public String transformContent(ChatroomDetail chatroomDetail, UserDto userDto){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        chatroomDetail.setContent(AES128.getAES128_Decode(chatroomDetail.getContent()));
        if(chatroomDetail.getRendezvousFlag() && !chatroomDetail.getSenderId().equals(userDto.getUserId())) {
            if (!chatroomDetail.getRendezvousLocation().equals(userDto.getUserLocation()) || currentTime.after(chatroomDetail.getRendezvousTime())) {
                chatroomDetail.setContent("hidden message");
            }
        }
        return chatroomDetail.getContent();
    }

    @Transactional
    public Long sendMessage(String user_id, Long chatroom_id, String content, Long rendezvous_time) {
        Chatroom chatroom = chatroomRepository.findByChatroomId(chatroom_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+chatroom_id));;
        User user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("No user with id "+user_id));;

        Message message = Message.builder()
                .sender(user)
                .chatroom(chatroom)
                .content(AES128.getAES128_Encode(content))
                .sendTime(new Timestamp(System.currentTimeMillis()))
                .build();

        chatroom.getMessages().add(message);
        chatroom.setLastMessage(message);

        if (!rendezvous_time.equals(-1L) && rendezvous_time != null){
            message.setRendezvousFlag(true);
            message.setRendezvousTime(new Timestamp(message.getSendTime().getTime() + (rendezvous_time * 60000L)));  // 60000 ms = 1 min
            message.setRendezvousLocation(user.getUserLocation());
        }
        else{
            message.setRendezvousFlag(false);
        }

        return messageRepository.save(message).getMessageId();
    }
}
