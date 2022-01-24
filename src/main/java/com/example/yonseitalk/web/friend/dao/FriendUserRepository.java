package com.example.yonseitalk.web.friend.dao;

import com.example.yonseitalk.web.user.dto.FriendUser;

import java.util.List;

public interface FriendUserRepository {

    List<FriendUser> findAll(String id);

}
