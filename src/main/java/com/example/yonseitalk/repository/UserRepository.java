package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);

    @Query("SELECT u FROM User u WHERE u.userLocation = ?1")
    List<User> findByLocation(String location);

}
