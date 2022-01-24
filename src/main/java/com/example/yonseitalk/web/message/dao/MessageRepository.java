package com.example.yonseitalk.web.message.dao;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Optional<Message> findById(Long id);

    Message save(Message message);

    List<Message> findByChatroomId(Long chatroom_id);

    int updateReadTime(Long id, Timestamp timestamp);

    Long countMessages(Long chatroom_id);
}
