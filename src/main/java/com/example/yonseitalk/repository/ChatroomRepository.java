package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository {

    Optional<Chatroom> findById(Long id);

    List<Chatroom> findByUser(String user_id);

    int save(Chatroom chatroom);

    int delete(Long id);

    // delete by user_id
    // 계정 회원 탈퇴시 사용
    int deleteAll(String user_id);
}
