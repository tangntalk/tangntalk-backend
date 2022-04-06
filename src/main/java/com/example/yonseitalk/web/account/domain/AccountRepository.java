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

    @Query(value = "select account.name as name, account.account_id as accountId, " +
            "account.status_message as statusMessage," +
            "account.type as type, account.connection_status as connectionStatus, " +
            "account.account_location as accountLocation, chatroom.chatroom_id as chatroomId " +
            "from account " +
            "left join chatroom  on :id in (chatroom.user_1,chatroom.user_2) and " +
            "account.account_id in (chatroom.user_1,chatroom.user_2) " +
            "where account.account_location = :location " +
            "and account.account_id <> :id", nativeQuery = true)
    List<NearByAccountProjection> findByNearLocation(@Param("id") String id, @Param("location") String location);

}
