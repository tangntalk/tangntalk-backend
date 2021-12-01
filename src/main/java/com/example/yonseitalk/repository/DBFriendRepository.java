package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.Friend;
import com.example.yonseitalk.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
public class DBFriendRepository implements FriendRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBFriendRepository(DataSource dataSource){
        jdbcTemplate =new JdbcTemplate(dataSource);
    }

    @Override
    public List<Friend> findByUserId(String id) {
        List<Friend> result=jdbcTemplate.query("select * from friends where user_id = ?", friendRowMapper(),id);
        return result;
    }

    @Override
    public List<Friend> findByFriendId(String id) {
        List<Friend> result=jdbcTemplate.query("select * from friends where friend_id = ?", friendRowMapper(),id);
        return result;
    }

    @Override
    public int save(Friend friend){


        String INSERT_QUERY = "insert into yonseitalk.friends (user_id,friend_id) values (?,?)";
        return jdbcTemplate.update(INSERT_QUERY, friend.getUser_id(),friend.getFriend_id());
//

//        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate).withTableName("friends");
//        SqlParameterSource param=new BeanPropertySqlParameterSource(friend);
//        return jdbcInsert.execute(param);

    }

    @Override
    public int delete(Friend friend){
        int status =jdbcTemplate.update("delete from friends " +
                "where user_id = ? and friend_id = ?",friend.getUser_id(),friend.getFriend_id());
        return status;

    }

    @Override
    public boolean isFriend(String userId, String friendId){
        List<Friend> result = jdbcTemplate.query("select * from friends where user_id = ? and friend_id = ?", friendRowMapper(), userId, friendId);
        return !result.isEmpty();
    }

    private RowMapper<Friend> friendRowMapper(){
        return new RowMapper<Friend>() {
            @Override
            public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
                Friend friend=new Friend();
                friend.setFriend_id(rs.getString("friend_id"));
                friend.setUser_id(rs.getString("user_id"));
                return friend;
            }
        };
    }
}
