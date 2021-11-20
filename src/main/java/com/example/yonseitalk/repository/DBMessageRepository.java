package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.domain.SearchUser;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DBMessageRepository implements MessageRepository{

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Message> findById(Long id) {
        List<Message> result = jdbcTemplate.query("select * from message where message_id = ?", messageRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Message save(Message message) {
        //추후 insert 문으로 바꿀 예정
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("message").usingGeneratedKeyColumns("message_id");
        SqlParameterSource param = new BeanPropertySqlParameterSource(message);
        Number key = jdbcInsert.executeAndReturnKey(param);
        message.setMessage_id(key.longValue());
        return message;
    }

    @Override
    public List<Message> findByChatroomId(Long chatroom_id) {
        return jdbcTemplate.query("select * from message where chatroom_id = ?", messageRowMapper(), chatroom_id);
    }

    private RowMapper<Message> messageRowMapper(){
        return (rs, rowNum) -> {
            Message message = new Message();
            message.setMessage_id(rs.getLong("message_id"));
            message.setChatroom_id(rs.getLong("chatroom_id"));
            message.setSender_id(rs.getString("sender_id"));
            message.setReceiver_id(rs.getString("receiver_id"));
            message.setContent(rs.getString("content"));
            message.setSend_time(rs.getTimestamp("send_time"));
            message.setRead_time(rs.getTimestamp("read_time"));
            message.setRendezvous_flag(rs.getBoolean("rendezvous_flag"));
            message.setRendezvous_location(rs.getString("rendezvous_location"));
            message.setRendezvous_time(rs.getTimestamp("rendezvous_time"));
            return message;
        };
    }
}
