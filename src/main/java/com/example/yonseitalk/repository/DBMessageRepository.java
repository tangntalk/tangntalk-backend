package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.domain.SearchUser;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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


        String INSERT_QUERY = "insert into yonseitalk.message (chatroom_id,sender_id,content,send_time,rendezvous_flag,rendezvous_location,rendezvous_time) values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (con) -> {
            PreparedStatement preparedStatement =con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            preparedStatement.setLong(1,message.getChatroom_id());
            preparedStatement.setString(2,message.getSender_id());
            preparedStatement.setString(3,message.getContent());
            preparedStatement.setTimestamp(4,message.getSend_time());
            preparedStatement.setBoolean(5,message.getRendezvous_flag());
            preparedStatement.setString(6,message.getRendezvous_location());
            preparedStatement.setTimestamp(7,message.getRendezvous_time());
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator,keyHolder);
        message.setMessage_id(keyHolder.getKey().longValue());
        return message;


        //추후 insert 문으로 바꿀 예정
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("message").usingGeneratedKeyColumns("message_id");
//        SqlParameterSource param = new BeanPropertySqlParameterSource(message);
//        Number key = jdbcInsert.executeAndReturnKey(param);
//        message.setMessage_id(key.longValue());
//        return message;
    }

    @Override
    public List<Message> findByChatroomId(Long chatroom_id) {
        return jdbcTemplate.query("select * from message where chatroom_id = ? order by send_time", messageRowMapper(), chatroom_id);
    }

    @Override
    public Long countMessages(Long chatroom_id){
        return jdbcTemplate.queryForObject("select count(*) from message where chatroom_id = ?", Long.class,  chatroom_id);
    }

    @Override
    public int updateReadTime(Long id,Timestamp timestamp) {
        return jdbcTemplate.update("update message set read_time = ? where message_id = ?",timestamp,id);
    }

    private RowMapper<Message> messageRowMapper(){
        return (rs, rowNum) -> {
            Message message = new Message();
            message.setMessage_id(rs.getLong("message_id"));
            message.setChatroom_id(rs.getLong("chatroom_id"));
            message.setSender_id(rs.getString("sender_id"));
//            message.setReceiver_id(rs.getString("receiver_id"));
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
