package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.ChatroomDetail;
import com.example.yonseitalk.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DBChatroomDetailRepository implements ChatroomDetailRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ChatroomDetail> findChatroomListbyUser(String user_id) {
        List<ChatroomDetail> result = jdbcTemplate.query("select chatroom.chatroom_id, user_1, user_2, sender_id, content, send_time, rendezvous_flag, rendezvous_location, rendezvous_time, connection_status\n" +
                "from (select * from chatroom, yt_user where (last_message_id is not null) and (user_1=? or user_2 = ?) and\n" +
                "((user_1 <> ? and user_1 = user_id) or\n" +
                "(user_2 <> ? and user_2 = user_id))) as chatroom\n" +
                "left join message m on chatroom.last_message_id = m.message_id\n" +
                "order by send_time desc;", chatroomDetailRowMapper(), user_id, user_id, user_id, user_id);
        return result;
    }

    private RowMapper<ChatroomDetail> chatroomDetailRowMapper(){
        return new RowMapper<ChatroomDetail>() {
            @Override
            public ChatroomDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                ChatroomDetail chatroom = new ChatroomDetail();
                chatroom.setChatroom_id(rs.getLong("chatroom_id"));
                chatroom.setUser_1(rs.getString("user_1"));
                chatroom.setUser_2(rs.getString("user_2"));
//                chatroom.setLast_message_id(rs.getLong("last_message_id"));
                chatroom.setConnection_status(rs.getBoolean("connection_status"));
                chatroom.setSender_id(rs.getString("sender_id"));
                chatroom.setContent(rs.getString("content"));
                chatroom.setSend_time(rs.getTimestamp("send_time"));
                chatroom.setRendezvous_flag(rs.getBoolean("rendezvous_flag"));
                chatroom.setRendezvous_location(rs.getString("rendezvous_location"));
                chatroom.setRendezvous_time(rs.getTimestamp("rendezvous_time"));
                return chatroom;
            }
        };
    }
}
