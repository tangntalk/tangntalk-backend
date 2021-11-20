package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository {

    Optional<Chatroom> findById(Long id);

    List<Chatroom> findByUser(String user_id);

    Chatroom save(Chatroom chatroom);

    int delete(Long id);

    //두개의 user_id로 특정 chatroom을 가져올 수 있는 findByPairUser
    Optional<Chatroom> findByPairUser(String user_id1,String user_id2);

    // delete by user_id
    // 계정 회원 탈퇴시 사용
    int deleteAll(String user_id);
}
