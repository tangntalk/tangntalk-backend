package com.example.yonseitalk.web.chatroom.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Optional<Chatroom> findByChatroomId(Long chatroomId);

    void deleteChatroomByChatroomId(Long chatrromId);

    @Query(value = "select * from chatroom where user_1 = ?1 or user_2 = ?1", nativeQuery = true)
    List<Chatroom> findByUserId(String user_id);

    //두개의 user_id로 특정 chatroom을 가져올 수 있는 findByPairUser
    @Query(value = "select * from chatroom where (user_1=?1 and user_2=?2) or (user_1=?2 and user_2=?1)", nativeQuery = true)
    Optional<Chatroom> findByPairUser(String user_id1,String user_id2);


}
