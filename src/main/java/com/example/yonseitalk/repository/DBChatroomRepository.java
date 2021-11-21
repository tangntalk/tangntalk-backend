package com.example.yonseitalk.repository;


import com.example.yonseitalk.domain.Chatroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DBChatroomRepository implements ChatroomRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBChatroomRepository(DataSource datasource) { jdbcTemplate = new JdbcTemplate(datasource); }

    @Override
    public Optional<Chatroom> findById(Long id) {
        List<Chatroom> result = jdbcTemplate.query("select * from chatroom where chatroom_id = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Chatroom> findByPairUser(String user_id1,String user_id2){
        List<Chatroom> result =jdbcTemplate.query("select * from chatroom where (user_1=? and user_2=?) or (user_1=? and user_2=?)",userRowMapper(),user_id1,user_id2,user_id2,user_id1);
        return result.stream().findAny();
    }// 테스트 코드 작성완료

    @Override
    public List<Chatroom> findByUser(String user_id){
        List<Chatroom> result = jdbcTemplate.query("select * from chatroom where user_1 = ? or user_2 = ?", userRowMapper(), user_id, user_id);
        return result;
    }
    @Override
    public Chatroom save(Chatroom chatroom){

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("chatroom").usingGeneratedKeyColumns("chatroom_id");
        SqlParameterSource param = new BeanPropertySqlParameterSource(chatroom);
        Number key = jdbcInsert.executeAndReturnKey(param);
        chatroom.setChatroom_id(key.longValue());
        return chatroom;
    }

    @Override
    public int updateLastMessage(Long chatroom_id, Long message_id) {
        return jdbcTemplate.update("update chatroom set last_message_id = ? where chatroom_id = ?",message_id,chatroom_id);
    }

    @Override
    public int delete(Long id){
        int status = jdbcTemplate.update("delete from chatroom where chatroom_id = ?", id);
        return status;
    }

    @Override
    public int deleteAll(String user_id){
        int status = jdbcTemplate.update("delete from chatroom where user_1 = ? or user_2 = ?", user_id, user_id);
        return status;
    }


    private RowMapper<Chatroom> userRowMapper(){
        return new RowMapper<Chatroom>() {
            @Override
            public Chatroom mapRow(ResultSet rs, int rowNum) throws SQLException {
                Chatroom chatroom = new Chatroom();
                chatroom.setChatroom_id(rs.getLong("chatroom_id"));
                chatroom.setUser_1(rs.getString("user_1"));
                chatroom.setUser_2(rs.getString("user_2"));
                chatroom.setLast_message_id(rs.getLong("last_message_id"));
                return chatroom;
            }
        };
    }
}
