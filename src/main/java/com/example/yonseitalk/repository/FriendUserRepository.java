package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.FriendUser;

import java.util.List;

public interface FriendUserRepository {

    List<FriendUser> findAll(String id);
    List<FriendUser> search(String searchQuery);

}
