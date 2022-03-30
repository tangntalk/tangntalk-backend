package com.example.yonseitalk.web.account.service;

import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.dto.*;

import java.util.List;
import java.util.Optional;

public interface AccountService {



    public void save(AccountDto.Request.Register accountRegisterRequest);

    public void save(AccountDto accountDto);

    public AccountInfoQueryResponse accountInfoQuery(String id);

    public void deleteById(String id);

    public Optional<AccountDto> findById(String id);

    public List<Account> findByLocation(String location);

    public void modifyInformation(String id , AccountDto.Request.ModifyInfo modifyInfo);

    public int updateAccountConnectionStatus(String id, Boolean flag);

    public FriendQueryResponse findFriendAccount(String accountId);

    public void addFriend(String userId, String friendId);

    public void delFriend(String userId, String friendId);

    public FriendDto.Response.FriendCheck isFriend(String userId, String friendId);

    public List<FriendSearchResponse> search(String accountId, String searchQuery);

    public AccountDto.Response.NearBy nearByQuery(String accountId , String targetLocation);


}
