package com.example.yonseitalk.web.chatroom.dao;

import com.example.yonseitalk.web.chatroom.dto.projection.ChatroomDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, String> {

    Optional<Chatroom> findByChatroomId(Long chatroomId);

    void deleteChatroomByChatroomId(Long chatrromId);

    @Query(value = "select * from chatroom where user_1 = ?1 or user_2 = ?1", nativeQuery = true)
    List<Chatroom> findByUserId(String user_id);

    //두개의 user_id로 특정 chatroom을 가져올 수 있는 findByPairUser
    @Query(value = "select * from chatroom where (user_1=?1 and user_2=?2) or (user_1=?2 and user_2=?1)", nativeQuery = true)
    Optional<Chatroom> findByPairUser(String user_id1,String user_id2);

    @Query(value = "select chatroom.chatroom_id as chatroomId, user_1 as user1, user_2 as user2, "+
            "sender_id as senderId, content as content, "+
            "send_time as sendTime, rendezvous_flag as rendezvousFlag, "+
            "rendezvous_location as rendezvousLocation, rendezvous_time as rendezvousTime, connection_status as connectionStatus " +
            "from (select * from chatroom, yt_user where (last_message_id is not null) and (user_1=?1 or user_2 = ?1) and " +
            "((user_1 <> ?1 and user_1 = user_id) or " +
            "(user_2 <> ?1 and user_2 = user_id))) as chatroom " +
            "left join message m on chatroom.last_message_id = m.message_id " +
            "order by send_time desc;", nativeQuery = true)
    List<ChatroomDetailProjection> findChatroomListbyUser(String user_id);
}
