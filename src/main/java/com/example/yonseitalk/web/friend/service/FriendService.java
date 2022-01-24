package com.example.yonseitalk.web.friend.service;

import com.example.yonseitalk.web.user.dto.FriendUser;

import java.util.List;

public interface FriendService {
    List<FriendUser> FindFriends(String userId);
    int addFriend(String userId, String friendId);
    int delFriend(String userId, String friendId);
    boolean isFriend(String userId, String friendId);
}
