//package com.example.yonseitalk.repository;
//
//import com.example.yonseitalk.AES128;
//import com.example.yonseitalk.domain.Friend;
//import com.example.yonseitalk.domain.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class DBUserRepository implements UserRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public DBUserRepository(DataSource dataSource){
//        jdbcTemplate =new JdbcTemplate(dataSource);
//    }
//
//    @Override
//    public Optional<User> findById(String id) {
//        List<User> result=jdbcTemplate.query("select * from yt_user where user_id = ?", userRowMapper(),id);
//        return result.stream().findAny();
//    }
//
//    @Override
//    public List<User> findByLocation(String location){
//        List<User> result=jdbcTemplate.query("select * from yt_user where user_location = ?",userRowMapper(),location);
//        return result;
//    }
//
//    @Override
//    public int save(User user){
//        //암호화
//        String userPwd= AES128.getAES128_Encode(user.getPassword());
//        user.setPassword(userPwd);
//
//        String INSERT_QUERY = "insert into yt_user (user_id, name,password,status_message,type,connection_status,user_location) values (?,?,?,?,?,?,?)";
//        return jdbcTemplate.update(INSERT_QUERY, user.getUser_id(),user.getName(),user.getPassword(),user.getStatus_message(),user.getType(),user.getConnection_status(),user.getUser_location());
////        //암호화
////        String userPwd= AES128.getAES128_Encode(user.getPassword());
////        user.setPassword(userPwd);
////
////        //암호화 끝
////        SimpleJdbcInsert jdbcInsert=new SimpleJdbcInsert(jdbcTemplate).withTableName("yt_user");
////        SqlParameterSource param=new BeanPropertySqlParameterSource(user);
////        return jdbcInsert.execute(param);
//
//
//    }
//
//    @Override
//    public int delete(String id) {
//        int status =jdbcTemplate.update("delete from yt_user where user_id = ?",id);
//        return status;
//
//    }
//    public int deleteAll(){
//        int status=jdbcTemplate.update("delete from yt_user");
//        return status;
//    }
//
//    @Override
//    public int updateStatusMessage(String id,String msg) {
//        String UPDATE_QUERY = "update yt_user set status_message = ? where user_id = ?";
//        int status =jdbcTemplate.update(UPDATE_QUERY,msg,id);
//        return status;
//    }
//
//    @Override
//    public int updateUserLocation(String id,String location) {
//        String UPDATE_QUERY = "update yt_user set user_location = ? where user_id = ?";
//        int status =jdbcTemplate.update(UPDATE_QUERY,location,id);
//        return status;
//    }
//
//    @Override
//    public int updateUserConnectionStatus(String id, Boolean flag){
//        String UPDATE_QUERY = "update yt_user set connection_status = ? where user_id = ?";
//        int status =jdbcTemplate.update(UPDATE_QUERY,flag,id);
//
//        return status;
//    }
//
//    private RowMapper<User> userRowMapper(){
//        return new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user=new User();
//                user.setName(rs.getString("name"));
//                user.setUser_id(rs.getString("user_id"));
//                user.setPassword(rs.getString("password"));
//                user.setUser_location(rs.getString("user_location"));
//                user.setStatus_message(rs.getString("status_message"));
//                user.setType(rs.getString("type"));
//                user.setConnection_status(rs.getBoolean("connection_status"));
//
//                return user;
//            }
//        };
//    }
//    private RowMapper<Friend> friendRowMapper(){
//        return new RowMapper<Friend>() {
//            @Override
//            public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Friend friend=new Friend();
//                friend.setFriend_id(rs.getString("friend_id"));
//                friend.setUser_id(rs.getString("user_id"));
//                return friend;
//            }
//        };
//    }
//}
