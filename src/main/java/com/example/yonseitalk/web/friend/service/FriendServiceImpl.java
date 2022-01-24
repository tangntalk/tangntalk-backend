package com.example.yonseitalk.web.friend.service;

import com.example.yonseitalk.web.friend.dao.Friend;
import com.example.yonseitalk.web.friend.dao.FriendUserRepository;
import com.example.yonseitalk.web.user.dto.FriendUser;
import com.example.yonseitalk.web.friend.dao.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final FriendUserRepository friendUserRepository;

    @Override
    public List<FriendUser> FindFriends(String userId) {
        return  friendUserRepository.findAll(userId);
    }

    @Override
    public int addFriend(String userId, String friendId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        return friendRepository.save(friend);
    }

    @Override
    public int delFriend(String userId, String friendId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        return friendRepository.delete(friend);
    }

    @Override
    public boolean isFriend(String userId, String friendId){
        return friendRepository.isFriend(userId, friendId);
    }
}
