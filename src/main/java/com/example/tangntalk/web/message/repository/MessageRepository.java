package com.example.tangntalk.web.message.repository;

import com.example.tangntalk.web.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByChatroomChatroomIdOrderBySendTimeAsc(Long chatroomId);

    Long countByChatroomChatroomId(Long chatroomId);
    Message findMessageByMessageId(Long messageId);

    default List<Message> findByChatroomId(Long chatroomId){
        return findByChatroomChatroomIdOrderBySendTimeAsc(chatroomId);
    }

    default Long countMessages(Long chatroomId){
        return countByChatroomChatroomId(chatroomId);
    }



}
