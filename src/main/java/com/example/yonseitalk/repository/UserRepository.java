package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);

    int save(User user); // 최초 가입 시 사용

    int delete(String id);

    void updateUserTime(String id,Timestamp timestamp); //로그인시 사용

    void updateStatusMessage(String id,String msg);

    void updateUserLocation(String id,String location);

}
