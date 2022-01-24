package com.example.yonseitalk.web.user.service;

import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.FriendUser;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.dto.UserRegisterRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    public void save(UserDto userDto);

    public UserDto save(UserRegisterRequest userRegisterRequest);

    public UserDto findById(String id);

    public void deleteById(String id);

    public List<User> findByLocation(String location);

    public int updateStatusMessage(String id, String msg);

    public int updateUserConnectionStatus(String id, Boolean flag);
    
    public int updateUserLocation(String id, String location);

    public Set<User> findFriendUser(String userId);

    public void addFriend(String userId, String friendId);

    public void delFriend(String userId, String friendId);

    public void isFriend(String userId, String friendId);

    public List<UserDto> search(String userId, String searchQuery);
}
