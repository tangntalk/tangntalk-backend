package com.example.yonseitalk.service;

import com.example.yonseitalk.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void save(User user);

    public Optional<User> findById(String id);

    public void deleteById(String id);

    public List<User> findByLocation(String location);

    public int updateStatusMessage(String id, String msg);

    public int updateUserConnectionStatus(String id, Boolean flag);
    
    public int updateUserLocation(String id, String location);
}
