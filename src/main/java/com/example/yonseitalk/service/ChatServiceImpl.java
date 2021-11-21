package com.example.yonseitalk.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.ChatroomWrapper;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.domain.User;
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


    @Override
    public Long addChatroom(String user_1_id, String user_2_id) {
        Chatroom chatroom = new Chatroom();
        chatroom.setUser_1(user_1_id);
        chatroom.setUser_2(user_2_id);
        chatroom = chatroomRepository.save(chatroom);
        return chatroom.getChatroom_id();
    }

    @Override
    public List<ChatroomWrapper> findChatroom(String user_id) {
        User user;
        List<ChatroomWrapper> chatroomWrapperList = new ArrayList<>();
        if(userRepository.findById(user_id).isPresent()){
            user = userRepository.findById(user_id).get();
        }
        else{
            return chatroomWrapperList;
        }
        List<Chatroom> chatroomList = chatroomRepository.findByUser(user_id);
        chatroomList.forEach(chatroom -> {
            if(chatroom.getLast_message_id()!=0){
                ChatroomWrapper chatroomWrapper = new ChatroomWrapper();
                chatroomWrapper.setChatroom(chatroom);
                Message message = readMessage(chatroom.getLast_message_id(),user);
                chatroomWrapper.setLast_message(message);
                chatroomWrapperList.add(chatroomWrapper);
            }
        });
        if (chatroomWrapperList.size()>=2){
            chatroomWrapperList.sort(Comparator.comparing(chatroomWrapper -> chatroomWrapper.getLast_message().getSend_time(),Comparator.reverseOrder()));
        }
        return chatroomWrapperList;
    }

    @Override
    public List<Message> messageInquiry(Long chatroom_id, String user_id) {
        User user = userRepository.findById(user_id).get();
        List<Message> messageList = messageRepository.findByChatroomId(chatroom_id);
        messageList.forEach(message -> {
            message.setContent(transformContent(message,user));
            //가려진 메시지도 읽었다고 처리했다 가정하자.
            if(message.getRead_time()==null && message.getReceiver_id().equals(user_id)){
                messageRepository.updateReadTime(message.getMessage_id(),new Timestamp(System.currentTimeMillis()));
            }

        });
        return messageList;
    }

    @Override
    public Message readMessage(Long message_id, User user) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            Message message = optionalMessage.get();
            message.setContent(transformContent(message, user));
            return message;
        }
        return null;
    }

    @Override
    public String transformContent(Message message, User user){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        message.setContent(AES128.getAES128_Decode(message.getContent()));
        if(message.getRendezvous_flag() && message.getReceiver_id().equals(user.getUser_id())) {
            if (!message.getRendezvous_location().equals(user.getUser_location()) || currentTime.after(message.getRendezvous_time())) {
                message.setContent("hidden message");
            }
        }
        return message.getContent();
    }

    @Override
    public Long sendMessage(String user_id, Long chatroom_id, String content, String rendezvous_location, Timestamp rendezvous_time) {
        Chatroom chatroom = chatroomRepository.findById(chatroom_id).orElse(null);
        if(chatroom ==null){
            return (long)-1;
        }
        Message message = new Message();
        message.setChatroom_id(chatroom_id);
        message.setSender_id(user_id);
        if (chatroom.getUser_1().equals(user_id)){
            message.setReceiver_id(chatroom.getUser_2());
        }
        else{
            message.setReceiver_id(chatroom.getUser_1());
        }
        User receiver = userRepository.findById(message.getReceiver_id()).orElse(null);
        if (receiver== null || !receiver.getConnection_status()){
            return (long)-1;
        }
        message.setContent(AES128.getAES128_Encode(content));
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        if (rendezvous_location!=null && rendezvous_time!=null){
            message.setRendezvous_flag(true);
            message.setRendezvous_time(rendezvous_time);
            message.setRendezvous_location(rendezvous_location);
        }
        else{
            message.setRendezvous_flag(false);
        }
        Long message_id =  messageRepository.save(message).getMessage_id();
        chatroomRepository.updateLastMessage(chatroom_id,message_id);
        return message_id;
    }
}
