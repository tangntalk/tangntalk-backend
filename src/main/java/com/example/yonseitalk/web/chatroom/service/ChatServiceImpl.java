package com.example.yonseitalk.web.chatroom.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.web.chatroom.dao.ChatroomDetailRepository;
import com.example.yonseitalk.web.chatroom.dao.ChatroomRepository;
import com.example.yonseitalk.web.message.dao.MessageRepository;
import com.example.yonseitalk.web.chatroom.dao.Chatroom;
import com.example.yonseitalk.web.chatroom.dao.ChatroomDetail;
import com.example.yonseitalk.web.message.dao.Message;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
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
        if(chatroomCheck.isPresent()) return chatroomCheck.get().getChatroomId();

        chatroom.setUser1(user_1_id);
        chatroom.setUser2(user_2_id);
        chatroom = chatroomRepository.save(chatroom);
        return chatroom.getChatroomId();
    }

    @Override
    public List<ChatroomDetail> findChatroom(String user_id) {

        Optional<UserDto> userDto = userService.findById(user_id);
        if(!userDto.isPresent())
            return new ArrayList<>();
        List<ChatroomDetail> chatroomDetailList = chatroomDetailRepository.findChatroomListbyUser(user_id);
        chatroomDetailList.forEach(chatroomDetail -> chatroomDetail.setContent(transformContent(chatroomDetail,userDto.get())));
        return chatroomDetailList;
    }

    @Override
    public List<Message> messageInquiry(Long chatroom_id, String user_id) {
        Optional<UserDto> userDto = userService.findById(user_id);
        if(!userDto.isPresent())
            return new ArrayList<>();

        List<Message> messageList = messageRepository.findByChatroomId(chatroom_id);
        messageList.forEach(message -> {
            message.setContent(transformContent(message,userDto.get()));
            //가려진 메시지도 읽었다고 처리했다 가정하자.
            if(message.getReadTime()==null && !message.getSenderId().equals(user_id)){
                messageRepository.updateReadTime(message.getMessageId(),new Timestamp(System.currentTimeMillis()));
            }
        });
        return messageList;
    }


    @Override
    public String transformContent(Message message, UserDto userDto){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        message.setContent(AES128.getAES128_Decode(message.getContent()));
        if(message.getRendezvousFlag() && !message.getSenderId().equals(userDto.getUserId())) {
            if (!message.getRendezvousLocation().equals(userDto.getUserLocation()) || currentTime.after(message.getRendezvousTime())) {
                message.setContent("hidden message");
            }
        }
        return message.getContent();
    }

    @Override
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

    @Override
    public Long sendMessage(String user_id, Long chatroom_id, String content, Long rendezvous_time) {
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroom_id);
        Optional<UserDto> userDto = userService.findById(user_id);
        if(!chatroom.isPresent() || !userDto.isPresent())
            return (long)-1;

        Message message = new Message();
        message.setChatroomId(chatroom_id);
        message.setSenderId(user_id);
        message.setContent(AES128.getAES128_Encode(content));
        message.setSendTime(new Timestamp(System.currentTimeMillis()));

        if (!rendezvous_time.equals(-1L)){
            message.setRendezvousFlag(true);
            message.setRendezvousTime(new Timestamp(message.getSendTime().getTime() + (rendezvous_time * 60000L)));  // 60000 ms = 1 min
            message.setRendezvousLocation(userDto.get().getUserLocation());
        }
        else{
            message.setRendezvousFlag(false);
        }

        Long message_id = messageRepository.save(message).getMessageId();
        chatroomRepository.updateLastMessage(chatroom_id,message_id);
        return message_id;
    }
}
