package com.example.yonseitalk.repository;


import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.ChatroomDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DBChatroomRepository implements ChatroomRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Chatroom> findById(Long id) {
        List<Chatroom> result = jdbcTemplate.query("select * from chatroom where chatroom_id = ?", chatroomRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Chatroom> findByPairUser(String user_id1,String user_id2){
        List<Chatroom> result =jdbcTemplate.query("select * from chatroom where (user_1=? and user_2=?) or (user_1=? and user_2=?)",chatroomRowMapper(),user_id1,user_id2,user_id2,user_id1);
        return result.stream().findAny();
    }// 테스트 코드 작성완료

    @Override
    public List<Chatroom> findByUser(String user_id){
        List<Chatroom> result = jdbcTemplate.query("select * from chatroom where user_1 = ? or user_2 = ?", chatroomRowMapper(), user_id, user_id);
        return result;

    }
    @Override
    public Chatroom save(Chatroom chatroom){

        String INSERT_QUERY = "insert into chatroom (user_1,user_2) values(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (con) -> {
            PreparedStatement preparedStatement =con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            preparedStatement.setString(1,chatroom.getUser_1());
            preparedStatement.setString(2,chatroom.getUser_2());
//            preparedStatement.setLong(3,chatroom.getLast_message_id());
            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator,keyHolder);
        chatroom.setChatroom_id(keyHolder.getKey().longValue());
        return chatroom;



//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("chatroom").usingGeneratedKeyColumns("chatroom_id");
//        SqlParameterSource param = new BeanPropertySqlParameterSource(chatroom);
//        Number key = jdbcInsert.executeAndReturnKey(param);
//        chatroom.setChatroom_id(key.longValue());
//        return chatroom;
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

    private RowMapper<Chatroom> chatroomRowMapper(){
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
