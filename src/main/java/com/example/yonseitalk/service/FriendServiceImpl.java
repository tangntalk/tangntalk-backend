package com.example.yonseitalk.service;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.FriendUser;
import com.example.yonseitalk.repository.FriendRepository;
import com.example.yonseitalk.repository.FriendUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService{
    private final FriendRepository friendRepository;
    private final FriendUserRepository friendUserRepository;

    @Autowired
    public FriendServiceImpl(FriendRepository friendRepository, FriendUserRepository friendUserRepository) {
        this.friendRepository = friendRepository;
        this.friendUserRepository = friendUserRepository;
    }

    @Override
    public List<FriendUser> FindFriends(String userId) {
        return  friendUserRepository.findAll(userId);
    }

    @Override
    public int addFriend(String userId, String friendId) {
        Friend friend = new Friend();
        friend.setUser_id(userId);
        friend.setFriend_id(friendId);
        return friendRepository.save(friend);
    }

    @Override
    public int delFriend(String userId, String friendId) {
        Friend friend = new Friend();
        friend.setUser_id(userId);
        friend.setFriend_id(friendId);
        return friendRepository.delete(friend);
    }

    @Override
    public boolean isFriend(String userId, String friendId){
        return friendRepository.isFriend(userId, friendId);
    }
}
