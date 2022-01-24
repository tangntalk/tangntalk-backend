package com.example.yonseitalk.web.friend.dao;

import com.example.yonseitalk.web.user.dto.FriendUser;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DBFriendUserRepository implements FriendUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FriendUser> findAll(String id) {
        return jdbcTemplate.query("select yt_user.name,yt_user.user_id as user_id, " +
                "yt_user.status_message," +
                "yt_user.type,yt_user.connection_status,yt_user.user_location as user_location,chatroom.chatroom_id as chatroom_id " +
                "from yt_user join friends on yt_user.user_id=friends.friend_id " +
                "left join chatroom  on friends.user_id in (chatroom.user_1,chatroom.user_2) and " +
                "friends.friend_id in (chatroom.user_1,chatroom.user_2) " +
                "where friends.user_id = ?", FriendUserRowMapper(),id);
    }
    private RowMapper<FriendUser> FriendUserRowMapper(){
        return (rs, rowNum) -> {
            FriendUser friendUser = new FriendUser();
            friendUser.setName(rs.getString("name"));
            friendUser.setUserId(rs.getString("user_id"));
            friendUser.setStatusMessage(rs.getString("status_message"));
            friendUser.setType(rs.getString("type"));
            friendUser.setConnectionStatus(rs.getBoolean("connection_status"));
            friendUser.setUserLocation(rs.getString("user_location"));
            String chatroomIdStr= rs.getString("chatroom_id");
            friendUser.setChatroomId(chatroomIdStr==null?null:Long.parseLong(chatroomIdStr));
            return friendUser;
        };
    }

}
