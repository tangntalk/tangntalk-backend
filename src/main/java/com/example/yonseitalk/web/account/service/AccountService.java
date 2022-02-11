package com.example.yonseitalk.web.account.service;

import com.example.yonseitalk.web.account.dto.FriendAccount;
import com.example.yonseitalk.web.account.dto.SearchAccount;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.dto.AccountRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    public void save(AccountDto accountDto);

    public AccountDto save(AccountRegisterRequest accountRegisterRequest);

    public Optional<AccountDto> findById(String id);

    public void deleteById(String id);

    public List<AccountDto> findByLocation(String location);

    public int updateStatusMessage(String id, String msg);

    public int updateAccountConnectionStatus(String id, Boolean flag);
    
    public int updateAccountLocation(String id, String location);

    public List<FriendAccount> findFriendAccount(String userId);

    public void addFriend(String userId, String friendId);

    public void delFriend(String userId, String friendId);

    public boolean isFriend(String userId, String friendId);

    public List<SearchAccount> search(String userId, String searchQuery);
}
