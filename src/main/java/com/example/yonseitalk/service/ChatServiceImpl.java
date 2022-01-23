package com.example.yonseitalk.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.domain.*;
import com.example.yonseitalk.repository.ChatroomDetailRepository;
import com.example.yonseitalk.repository.ChatroomRepository;
import com.example.yonseitalk.repository.MessageRepository;
import com.example.yonseitalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatroomDetailRepository chatroomDetailRepository;


    @Override
    public Long getMessageCount(Long chatroom_id){
        if(chatroom_id<0) return 0L;
        return messageRepository.countMessages(chatroom_id);
    }
    @Override
    public Long addChatroom(String user_1_id, String user_2_id) {
        Chatroom chatroom = new Chatroom();

        Optional<Chatroom> chatroomCheck = chatroomRepository.findByPairUser(user_1_id, user_2_id);
        if(chatroomCheck.isPresent()) return chatroomCheck.get().getChatroom_id();

        chatroom.setUser_1(user_1_id);
        chatroom.setUser_2(user_2_id);
        chatroom = chatroomRepository.save(chatroom);
        return chatroom.getChatroom_id();
    }

    @Override
    public List<ChatroomDetail> findChatroom(String user_id) {

        Optional<User> user = userRepository.findById(user_id);
        if(!user.isPresent())
            return new ArrayList<>();
        List<ChatroomDetail> chatroomDetailList = chatroomDetailRepository.findChatroomListbyUser(user_id);
        chatroomDetailList.forEach(chatroomDetail -> chatroomDetail.setContent(transformContent(chatroomDetail,user.get())));
        return chatroomDetailList;
    }

    @Override
    public List<Message> messageInquiry(Long chatroom_id, String user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if(!user.isPresent())
            return new ArrayList<>();

        List<Message> messageList = messageRepository.findByChatroomId(chatroom_id);
        messageList.forEach(message -> {
            message.setContent(transformContent(message,user.get()));
            //가려진 메시지도 읽었다고 처리했다 가정하자.
            if(message.getRead_time()==null && !message.getSender_id().equals(user_id)){
                messageRepository.updateReadTime(message.getMessage_id(),new Timestamp(System.currentTimeMillis()));
            }
        });
        return messageList;
    }


    @Override
    public String transformContent(Message message, User user){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        message.setContent(AES128.getAES128_Decode(message.getContent()));
        if(message.getRendezvous_flag() && !message.getSender_id().equals(user.getUserId())) {
            if (!message.getRendezvous_location().equals(user.getUserLocation()) || currentTime.after(message.getRendezvous_time())) {
                message.setContent("hidden message");
            }
        }
        return message.getContent();
    }

    @Override
    public String transformContent(ChatroomDetail chatroomDetail, User user){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        chatroomDetail.setContent(AES128.getAES128_Decode(chatroomDetail.getContent()));
        if(chatroomDetail.getRendezvous_flag() && !chatroomDetail.getSender_id().equals(user.getUserId())) {
            if (!chatroomDetail.getRendezvous_location().equals(user.getUserLocation()) || currentTime.after(chatroomDetail.getRendezvous_time())) {
                chatroomDetail.setContent("hidden message");
            }
        }
        return chatroomDetail.getContent();
    }

    @Override
    public Long sendMessage(String user_id, Long chatroom_id, String content, Long rendezvous_time) {
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroom_id);
        Optional<User> user = userRepository.findById(user_id);
        if(!chatroom.isPresent() || !user.isPresent())
            return (long)-1;

        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id(user_id);
        message.setContent(AES128.getAES128_Encode(content));
        message.setSend_time(new Timestamp(System.currentTimeMillis()));

        if (!rendezvous_time.equals(-1L)){
            message.setRendezvous_flag(true);
            message.setRendezvous_time(new Timestamp(message.getSend_time().getTime() + (rendezvous_time * 60000L)));  // 60000 ms = 1 min
            message.setRendezvous_location(user.get().getUserLocation());
        }
        else{
            message.setRendezvous_flag(false);
        }

        Long message_id = messageRepository.save(message).getMessage_id();
        chatroomRepository.updateLastMessage(chatroom_id,message_id);
        return message_id;
    }
}
