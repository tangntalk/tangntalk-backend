package com.example.tangntalk.web.account.service;

import com.example.tangntalk.exception.DuplicateAccountException;
import com.example.tangntalk.exception.NotFoundException;
import com.example.tangntalk.security.authorization.role.Role;
import com.example.tangntalk.web.account.domain.AccountQdslRepository;
import com.example.tangntalk.web.account.dto.*;
import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountQdslRepository accountQdslRepository;

    @Transactional
    public void save(AccountDto.Request.Register accountRegisterRequest) {
        accountRepository.findAccountByUsername(accountRegisterRequest.getUsername())
                .ifPresent(user -> {throw new DuplicateAccountException();});
//        accountRegisterRequest.setPassword(AES128.getAES128_Encode(accountRegisterRequest.getPassword()));
        Account account = accountRegisterRequest.toEntity();
        account.setConnectionStatus(false);
        account.setStatusMessage("");
        account.setAccountLocation("공학관");
        account.setRole(Role.NORMAL);
        accountRepository.save(account);
    }

    public void save(AccountDto accountDto) {
        accountRepository.save(accountDto.toAccount());
    }

    public AccountInfoDto getAccountInfo(String username) {
        Account account = accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);
        return AccountInfoDto.fromAccount(account);
    }

    @Transactional
    public Optional<AccountDto> findByUsername(String username){
        Account user = accountRepository.findAccountByUsername(username).orElse(null);
        return user==null?Optional.empty():Optional.of(AccountDto.fromAccount(user));
    }

    @Transactional
    public void deleteById(String id){
        accountRepository.deleteById(id);
    }

    // DTO로 반환해야함. 현재 Entity를 반환하고 있음
//    @Transactional
//    public List<Account> findByLocation(String location){
//        return accountRepository.findByAccountLocation(location);
//    }

    @Transactional
    public void modifyInformation(String username, AccountDto.Request.ModifyInfo modifyInfo) {
        Account account = accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);
        if (modifyInfo.getAccountLocation()!=null){
            account.setAccountLocation(modifyInfo.getAccountLocation());
        }
        if (modifyInfo.getStatusMessage()!=null){
            account.setStatusMessage(modifyInfo.getStatusMessage());
        }
    }

    @Transactional
    public void updateAccountConnectionStatus(String username, Boolean flag){
        Account account = accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);
        account.setConnectionStatus(flag);
    }

    @Transactional
    public OnlineAndOfflineFriendListDto findFriendAccount(String username){
        Account requestAccount = accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);
        return OnlineAndOfflineFriendListDto.fromFriendDtoList(accountQdslRepository.friendQuery(requestAccount));
    }

    @Transactional
    public void addFriend(String userId, String friendId){
        Account user = accountRepository.findAccountByUsername(userId).orElseThrow(NotFoundException::new);
        Account friend = accountRepository.findAccountByUsername(friendId).orElseThrow(NotFoundException::new);
        user.getAccountAddedFriends().add(friend);
        friend.getFriendsAddedAccount().add(user);
    }

    @Transactional
    public void deleteFriend(String userId, String friendId){
        Account user = accountRepository.findAccountByUsername(userId).orElseThrow(NotFoundException::new);
        Account friend = accountRepository.findAccountByUsername(friendId).orElseThrow(NotFoundException::new);
        user.getAccountAddedFriends().remove(friend);
        friend.getFriendsAddedAccount().remove(user);
    }

    @Transactional
    public FriendDto.Response.FriendCheck isFriend(String username, String friendId){
        Account account = accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);
        Account friend = accountRepository.findAccountByUsername(friendId).orElseThrow(NotFoundException::new);
        return new FriendDto.Response.FriendCheck(account.getAccountAddedFriends().contains(friend));
    }

    @Transactional
    public List<FriendSearchResponse> searchByNameOrUsername(String username, String searchQuery){
        Account requestAccount = accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);

        List<Account> searchAccountList = accountQdslRepository.search(username,searchQuery);
        Set<Account> friendList = accountRepository.findByFriendsAddedAccountContains(requestAccount);

        return searchAccountList
                .stream()
                .map(account -> {
                    return FriendSearchResponse
                            .fromAccount(account,friendList.contains(account));
                })
                .collect(Collectors.toList());

    }

    public AccountDto.Response.NearBy nearByQuery(String username,String targetLocation) {
        accountRepository.findAccountByUsername(username).orElseThrow(NotFoundException::new);
        return new AccountDto.Response.NearBy(accountRepository.findByNearLocation(username,targetLocation));
    }

}
