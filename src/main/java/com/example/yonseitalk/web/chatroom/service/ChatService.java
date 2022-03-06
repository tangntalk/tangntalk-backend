package com.example.yonseitalk.web.chatroom.service;

import com.example.yonseitalk.web.chatroom.dto.ChatroomDto;
import com.example.yonseitalk.web.message.dto.MessageDto;

import java.util.List;

public interface ChatService {

    Long addChatroom(String user_1_id, String user_2_id);
    Long getMessageCount(Long chatroom_id);
    List<MessageDto.SingleMessage> messageInquiry(Long chatroom_id, String user_id);
    Long sendMessage(String user_id , Long chatroom_id, String content, Long rendezvous_time);
    List<ChatroomDto.SingleChatroom> createChatroomView(String userId);

}
