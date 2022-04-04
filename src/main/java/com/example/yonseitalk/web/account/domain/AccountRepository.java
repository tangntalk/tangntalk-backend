package com.example.yonseitalk.web.account.domain;

import com.example.yonseitalk.web.account.dto.projection.FriendAccountProjection;
import com.example.yonseitalk.web.account.dto.projection.NearByAccountProjection;
import com.example.yonseitalk.web.account.dto.projection.SearchAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findById(String id);


    Set<Account> findByFriendsAddedAccountContains(Account account);

    List<Account> findByAccountLocation(String location);

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
