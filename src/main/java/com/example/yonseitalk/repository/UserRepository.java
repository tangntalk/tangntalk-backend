package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);

    int save(User user); // 최초 가입 시 사용

    int delete(String id);

    int updateStatusMessage(String id,String msg);

    int updateUserLocation(String id,String location);

    int updateUserConnectionStatus(String id, Boolean flag);

}
