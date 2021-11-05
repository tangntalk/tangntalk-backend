package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.SearchUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class DBSearchUserRepository implements SearchUserRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBSearchUserRepository(DataSource dataSource){
        jdbcTemplate =new JdbcTemplate(dataSource);
    }
    @Override
    public List<SearchUser> search(String id, String searchQuery) {
        //sql injection 미처리
        return jdbcTemplate.query("select yt_user.name,yt_user.user_id as user_id, " +
                        "yt_user.status_message," +
                        "yt_user.type,yt_user.connection_status,fr.user_id as my_id " +
                        "from yt_user left join " +
                        "(select * from friends where friends.user_id = '"+id+"') as fr " +
                        "on yt_user.user_id=fr.friend_id " +
                        "where yt_user.user_id like '%"+searchQuery+"%' or " +
                        "yt_user.name like '%"+searchQuery+"%' and not yt_user.user_id = '"+id+"'"
                , FriendUserRowMapper());
    }
    private RowMapper<SearchUser> FriendUserRowMapper(){
        return (rs, rowNum) -> {
            SearchUser searchUser = new SearchUser();
            searchUser.setName(rs.getString("name"));
            searchUser.setUser_id(rs.getString("user_id"));
            searchUser.setStatus_message(rs.getString("status_message"));
            searchUser.setType(rs.getString("type"));
            searchUser.setIsFriend(
                    Optional.ofNullable(rs.getString("my_id")).isPresent()
            );
            return searchUser;
        };
    }
}
