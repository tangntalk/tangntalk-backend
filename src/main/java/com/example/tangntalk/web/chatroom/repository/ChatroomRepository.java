package com.example.tangntalk.web.chatroom.repository;

import com.example.tangntalk.web.chatroom.domain.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Optional<Chatroom> findByChatroomId(Long chatroomId);

    void deleteChatroomByChatroomId(Long chatrromId);

    @Query(value = "select * from chatroom where user1_id = ?1 or user2_id = ?1", nativeQuery = true)
    List<Chatroom> findByUserId(String userId);

    //두개의 userId로 특정 chatroom을 가져올 수 있는 findByPairUser
    @Query(value = "select * from chatroom where (user1_id=?1 and user2_id=?2) or (user1_id=?2 and user2_id=?1)", nativeQuery = true)
    Optional<Chatroom> findChatroomByUserPair(Long userId1, Long userId2);


}
