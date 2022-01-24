package com.example.yonseitalk.web.message.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DBMessageRepository implements MessageRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Message> findById(Long id) {
        List<Message> result = jdbcTemplate.query("select * from message where message_id = ?", messageRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Message save(Message message) {


        String INSERT_QUERY = "insert into message (chatroom_id,sender_id,content,send_time,rendezvous_flag,rendezvous_location,rendezvous_time) values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (con) -> {
            PreparedStatement preparedStatement =con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            preparedStatement.setLong(1,message.getChatroomId());
            preparedStatement.setString(2,message.getSenderId());
            preparedStatement.setString(3,message.getContent());
            preparedStatement.setTimestamp(4,message.getSendTime());
            preparedStatement.setBoolean(5,message.getRendezvousFlag());
            preparedStatement.setString(6,message.getRendezvousLocation());
            preparedStatement.setTimestamp(7,message.getRendezvousTime());
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator,keyHolder);
        message.setMessageId(keyHolder.getKey().longValue());
        return message;


        //추후 insert 문으로 바꿀 예정
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("message").usingGeneratedKeyColumns("message_id");
//        SqlParameterSource param = new BeanPropertySqlParameterSource(message);
//        Number key = jdbcInsert.executeAndReturnKey(param);
//        message.setMessageId(key.longValue());
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
            message.setMessageId(rs.getLong("message_id"));
            message.setChatroomId(rs.getLong("chatroom_id"));
            message.setSenderId(rs.getString("sender_id"));
//            message.setReceiver_id(rs.getString("receiver_id"));
            message.setContent(rs.getString("content"));
            message.setSendTime(rs.getTimestamp("send_time"));
            message.setReadTime(rs.getTimestamp("read_time"));
            message.setRendezvousFlag(rs.getBoolean("rendezvous_flag"));
            message.setRendezvousLocation(rs.getString("rendezvous_location"));
            message.setRendezvousTime(rs.getTimestamp("rendezvous_time"));
            return message;
        };
    }
}
