package com.example.yonseitalk.web.account.service;

import com.example.yonseitalk.web.account.dto.*;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    public void save(AccountDto accountDto);

    public AccountDto save(AccountRegisterRequest accountRegisterRequest);

    public AccountInfoQueryResponse accountInfoQuery(String id);

    public Optional<AccountDto> findById(String id);

    public void deleteById(String id);

    public List<AccountDto> findByLocation(String location);

    public void modifyInformation(String id , AccountDtoMerged.Request.ModifyInfo modifyInfo);

    public int updateStatusMessage(String id, String msg);

    public int updateAccountConnectionStatus(String id, Boolean flag);
    
    public int updateAccountLocation(String id, String location);

    public FriendDto.Response.FriendQuery findFriendAccount(String accountId);

    public void addFriend(String userId, String friendId);

    public void delFriend(String userId, String friendId);

    public FriendDto.Response.FriendCheck isFriend(String userId, String friendId);

    public List<FriendDto.Response.SearchFriend> search(String accountId, String searchQuery);
}
