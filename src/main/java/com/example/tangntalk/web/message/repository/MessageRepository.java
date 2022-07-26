package com.example.tangntalk.web.message.repository;

import com.example.tangntalk.web.message.domain.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collections;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    Long countByChatroomChatroomId(Long chatroomId);

//    Message findMessageByMessageId(Long messageId);

    default List<Message> findMessageListByChatroomId(Long chatroomId, Pageable pageable){
        List<Message> result = findMessageListByChatroomIdReverse(chatroomId, pageable);
        Collections.reverse(result);
        return result;
    }

    @Query(
            "SELECT m " +
            "FROM Message m " +
            "INNER JOIN m.sender " +
            "WHERE m.chatroom.chatroomId = ?1 " +
            "ORDER BY m.sendTime DESC"
    )
    List<Message> findMessageListByChatroomIdReverse(Long chatroomId, Pageable pageable);

    default Long countMessages(Long chatroomId){
        return countByChatroomChatroomId(chatroomId);
    }



}
