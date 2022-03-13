package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.projection.FriendAccountProjection;
import com.example.yonseitalk.web.account.dto.projection.NearByAccountProjection;
import com.example.yonseitalk.web.account.dto.projection.SearchAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findById(String id);

    List<Account> findByAccountLocation(String location);

//    @Query("SELECT u FROM Account u WHERE u.accountLocation = ?1")
//    List<Account> findByLocation(String location);

    @Query(value = "SELECT yt_account.account_id as accountId, yt_account.name as name," +
            "yt_account.status_message as statusMessage," +
            "yt_account.type as type, fr.account_id as isFriend " +
            "FROM yt_account LEFT JOIN " +
            "(SELECT * from friends where friends.account_id = ?1) as fr " +
            "on yt_account.account_id=fr.friend_id " +
            "where yt_account.account_id like '%?2%' or " +
            "yt_account.name like '%?2%' and not yt_account.account_id = ?1 " +
            "order by yt_account.name", nativeQuery = true)
    List<SearchAccountProjection> search(@Param("id") String id, @Param("searchQuery") String searchQuery);

    @Query(value = "select yt_account.name as name,yt_account.account_id as userId, " +
            "yt_account.status_message as statusMessage," +
            "yt_account.type as type, yt_account.connection_status as connectionStatus, " +
            "yt_account.account_location as userLocation,chatroom.chatroom_id as chatroomId " +
            "from yt_account join friends on yt_account.account_id=friends.friend_id " +
            "left join chatroom  on friends.account_id in (chatroom.user_1,chatroom.user_2) and " +
            "friends.friend_id in (chatroom.user_1,chatroom.user_2) " +
            "where friends.account_id = ?", nativeQuery = true)
    List<FriendAccountProjection> findAll(String id);

    @Query(value = "select yt_account.name as name,yt_account.account_id as userId, " +
            "yt_account.status_message as statusMessage," +
            "yt_account.type as type, yt_account.connection_status as connectionStatus, " +
            "yt_account.account_location as userLocation,chatroom.chatroom_id as chatroomId " +
            "from yt_account " +
            "left join chatroom  on :id in (chatroom.user_1,chatroom.user_2) and " +
            "yt_account.account_id in (chatroom.user_1,chatroom.user_2) " +
            "where yt_account.account_location = :location " +
            "and yt_account.account_id <> :id", nativeQuery = true)
    List<NearByAccountProjection> findByNearLocation(@Param("id") String id, @Param("location") String location);

}
