package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.User;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    List<Friend> findByUserId(String id);

    List<Friend> findByFriendId(String id);

    int save(Friend friend); // 최초 등록시 사용

    int delete(String friend_id);


}
