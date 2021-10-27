package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(int id);

    void save(User user); // 최초 가입 시 사용

    void delete(User user);

    List<User> findFriend(String query);

    void changeUserTime(Timestamp timestamp); //로그인시 사용

    void changeStatusMessage(String msg);

    void changeUserLocation(String location);

}
