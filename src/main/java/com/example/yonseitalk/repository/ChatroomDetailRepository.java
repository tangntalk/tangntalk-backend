package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.ChatroomDetail;
import com.example.yonseitalk.domain.User;

import java.util.List;

public interface ChatroomDetailRepository {

    List<ChatroomDetail> findChatroomListbyUser(String user_id);
}
