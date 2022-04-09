package com.example.tangntalk.web.account.repository;

import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.dto.projection.FriendAccountProjection;
import com.example.tangntalk.web.account.dto.projection.NearByAccountProjection;
import com.example.tangntalk.web.account.dto.projection.SearchAccountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findById(String id);


    Set<Account> findByFriendsContains(Account account);

    List<Account> findByAccountLocation(String location);

    @Query(value = "select account.name as name, account.username as username, " +
            "account.status_message as statusMessage," +
            "account.account_type as type, account.connection_status as connectionStatus, " +
            "account.account_location as accountLocation, chatroom.chatroom_id as chatroomId " +
            "from account " +
            "left join chatroom  on :username in (chatroom.user1_id,chatroom.user2_id) and " +
            "account.username in (chatroom.user1_id,chatroom.user2_id) " +
            "where account.account_location = :location " +
            "and account.username <> :username", nativeQuery = true)
    List<NearByAccountProjection> findByNearLocation(@Param("username") String username, @Param("location") String location);

    Optional<Account> findAccountByUsername(String username);

}
