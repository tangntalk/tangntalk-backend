package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class DBUserRepository implements UserRepository {

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> findFriend(String query) {
        return null;
    }

    @Override
    public void changeUserTime(Timestamp timestamp) {

    }

    @Override
    public void changeStatusMessage(String msg) {

    }

    @Override
    public void changeUserLocation(String location) {

    }
}
