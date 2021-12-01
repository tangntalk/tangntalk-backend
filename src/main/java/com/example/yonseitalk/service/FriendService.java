package com.example.yonseitalk.service;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.FriendUser;
import com.example.yonseitalk.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FriendService {
    List<FriendUser> FindFriends(String userId);
    int addFriend(String userId, String friendId);
    int delFriend(String userId, String friendId);
    boolean isFriend(String userId, String friendId);
}
