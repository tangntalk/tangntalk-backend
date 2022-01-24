package com.example.yonseitalk.web.chatroom.dao;

import java.util.List;

public interface ChatroomDetailRepository {

    List<ChatroomDetail> findChatroomListbyUser(String user_id);
}
