package com.example.yonseitalk.service;

import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.ChatroomWrapper;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.domain.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ChatService {
    //채팅방 추가
    Long addChatroom(String user_1_id, String user_2_id);

    //채팅방 리스트 구하기 (채팅창이 만들어졌어도 메시지가 한번도 안보내진 채팅방은 안보이게 구현을 했다)
    List<ChatroomWrapper> findChatroom(String user_id);

    List<Message> messageInquiry(Long chatroom_id, String user_id);
    Message readMessage(Long message_id, User user);
    String transformContent(Message message, User user);
    Long sendMessage(String user_id , Long chatroom_id, String content, String rendezvous_location , Timestamp rendezvous_time);
}
