package com.example.yonseitalk.web.friend.dao;

import java.util.List;

public interface FriendRepository {

    List<Friend> findByUserId(String id);
    List<Friend> findByFriendId(String id);
    int save(Friend friend); // 최초 등록시 사용
    int delete(Friend friend);
    boolean isFriend(String userId, String friendId);

}
