package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.FriendUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DBFriendUserRepository implements FriendUserRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBFriendUserRepository(DataSource dataSource){
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    @Override
    public List<FriendUser> findAll(String id) {
        return jdbcTemplate.query("select yt_user.name,yt_user.user_id as user_id, " +
                "yt_user.status_message," +
                "yt_user.type,yt_user.connection_status,chatroom.chatroom_id as chatroom_id " +
                "from yt_user join friends on yt_user.user_id=friends.friend_id " +
                "left join chatroom  on friends.user_id in (chatroom.user_1,chatroom.user_2) and " +
                "friends.friend_id in (chatroom.user_1,chatroom.user_2) " +
                "where friends.user_id = ?", FriendUserRowMapper(),id);
    }
    private RowMapper<FriendUser> FriendUserRowMapper(){
        return (rs, rowNum) -> {
            FriendUser friendUser = new FriendUser();
            friendUser.setName(rs.getString("name"));
            friendUser.setUser_id(rs.getString("user_id"));
            friendUser.setStatus_message(rs.getString("status_message"));
            friendUser.setType(rs.getString("type"));
            friendUser.setConnection_status(rs.getBoolean("connection_status"));
            friendUser.setChatroomId(rs.getLong("chatroom_id"));
            return friendUser;
        };
    }

}
