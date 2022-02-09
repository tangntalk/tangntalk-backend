package com.example.yonseitalk.web.user.service;

import com.example.yonseitalk.web.user.dto.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    public void save(UserDto userDto);

    public UserDto save(UserRegisterRequest userRegisterRequest);

    public UserInfoQueryResponse userInfoQuery(String id);

    public Optional<UserDto> findById(String id);

    public void deleteById(String id);

    public List<UserDto> findByLocation(String location);

    public int updateStatusMessage(String id, String msg);

    public int updateUserConnectionStatus(String id, Boolean flag);
    
    public int updateUserLocation(String id, String location);

    public List<FriendUser> findFriendUser(String userId);

    public void addFriend(String userId, String friendId);

    public void delFriend(String userId, String friendId);

    public boolean isFriend(String userId, String friendId);

    public List<SearchUser> search(String userId, String searchQuery);
}
