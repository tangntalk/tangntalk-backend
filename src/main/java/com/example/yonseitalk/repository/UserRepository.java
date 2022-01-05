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

    @Query("SELECT u FROM User u WHERE u.user_location = ?1")
    List<User> findByLocation(String location);

    @Modifying
    @Query("UPDATE User u SET u.status_message = ?2 WHERE u.user_id = ?1")
    int updateStatusMessage(String id,String msg);

    @Modifying
    @Query("UPDATE User u SET u.user_location = ?2 WHERE u.user_id = ?1")
    int updateUserLocation(String id,String location);

    @Modifying
    @Query("UPDATE User u SET u.connection_status = ?2 WHERE u.user_id = ?1")
    int updateUserConnectionStatus(String id, Boolean flag);

}
