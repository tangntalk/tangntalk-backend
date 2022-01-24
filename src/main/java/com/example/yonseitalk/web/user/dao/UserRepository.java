package com.example.yonseitalk.web.user.dao;

import com.example.yonseitalk.web.user.dto.FriendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findById(String id);

    @Query("SELECT u FROM User u WHERE u.userLocation = ?1")
    List<User> findByLocation(String location);

    @Query(value = "SELECT yt_user.name,yt_user.user_id as user_id, " +
            "yt_user.status_message," +
            "yt_user.type,yt_user.connection_status,fr.user_id as my_id " +
            "FROM yt_user LEFT JOIN " +
            "(SELECT * from friends where friends.user_id = ?1) as fr " +
            "on yt_user.user_id=fr.friend_id " +
            "where yt_user.user_id like '%?2%' or " +
            "yt_user.name like '%?2%' and not yt_user.user_id = ?1 " +
            "order by yt_user.name", nativeQuery = true)
    List<User> search(String id, String searchQuery);

    @Query(value ="select yt_user.name,yt_user.user_id as user_id, " +
            "yt_user.status_message," +
            "yt_user.type,yt_user.connection_status,yt_user.user_location as user_location,chatroom.chatroom_id as chatroom_id " +
            "from yt_user join friends on yt_user.user_id=friends.friend_id " +
            "left join chatroom  on friends.user_id in (chatroom.user_1,chatroom.user_2) and " +
            "friends.friend_id in (chatroom.user_1,chatroom.user_2) " +
            "where friends.user_id = ?", nativeQuery = true)

    List<FriendUser> findAllFriends(String userId);


}
