package com.example.tangntalk.web.account.service;

import com.example.tangntalk.exception.ErrorType;
import com.example.tangntalk.exception.GlobalException;
import com.example.tangntalk.security.authorization.role.Role;
import com.example.tangntalk.web.account.repository.AccountQdslRepository;
import com.example.tangntalk.web.account.dto.*;
import com.example.tangntalk.web.account.domain.Account;
import com.example.tangntalk.web.account.repository.AccountRepository;
import com.example.tangntalk.web.account.dto.request.AccountRegisterDto;
import com.example.tangntalk.web.account.dto.request.ModifyInfoDto;
import com.example.tangntalk.web.account.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(AccountRegisterDto accountRegisterDto) {
        accountRepository.findAccountByUsername(accountRegisterDto.getUsername())
                .ifPresent(user -> {throw new GlobalException(HttpStatus.CONFLICT, ErrorType.DUPLICATED_ACCOUNT_ERROR);});
        Account account = Account.builder()
                .username(accountRegisterDto.getUsername())
                .name(accountRegisterDto.getName())
                .password(passwordEncoder.encode(accountRegisterDto.getPassword()))
                .accountType(accountRegisterDto.getType())
                .role(Role.of(accountRegisterDto.getType()))
                .connectionStatus(false)
                .statusMessage("")
                .accountLocation("공학관")
                .build();
        accountRepository.save(account);
    }

    public void save(AccountDto accountDto) {
        accountRepository.save(accountDto.toAccount());
    }

    public AccountInfoDto getAccountInfo(String username) {
        Account account = findAccountByUsername(username);
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
    public void modifyInformation(String username, ModifyInfoDto modifyInfoDto) {
        Account account = findAccountByUsername(username);
        if (modifyInfoDto.getAccountLocation()!=null){
            account.setAccountLocation(modifyInfoDto.getAccountLocation());
        }
        if (modifyInfoDto.getStatusMessage()!=null){
            account.setStatusMessage(modifyInfoDto.getStatusMessage());
        }
    }

    @Transactional
    public void updateAccountConnectionStatus(String username, Boolean flag){
        Account account = findAccountByUsername(username);
        account.setConnectionStatus(flag);
    }

    @Transactional
    public OnlineAndOfflineFriendListDto findFriendAccount(String username){
        Account requestAccount = findAccountByUsername(username);
        return OnlineAndOfflineFriendListDto.fromFriendDtoList(accountQdslRepository.friendQuery(requestAccount));
    }

    @Transactional
    public void addFriend(String userId, String friendUsername){
        Account user = findAccountByUsername(userId);
        Account friend = findAccountByUsername(friendUsername);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
    }

    @Transactional
    public void deleteFriend(String userId, String friendUsername){
        Account user = findAccountByUsername(userId);
        Account friend = findAccountByUsername(friendUsername);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
    }

    @Transactional
    public FriendDto.Response.FriendCheck isFriend(String username, String friendUsername){
        Account account = findAccountByUsername(username);
        Account friend = findAccountByUsername(friendUsername);
        return new FriendDto.Response.FriendCheck(account.getFriends().contains(friend));
    }

    @Transactional
    public List<FriendSearchDto> searchByNameOrUsername(String username, String searchQuery){
        Account requestAccount = findAccountByUsername(username);

        List<Account> searchAccountList = accountQdslRepository.search(username,searchQuery);
        Set<Account> friendList = accountRepository.findByFriendsContains(requestAccount);

        return searchAccountList
                .stream()
                .map(account -> {
                    return FriendSearchDto
                            .fromAccount(account,friendList.contains(account));
                })
                .collect(Collectors.toList());

    }

    public NearByFriendsDto nearByQuery(String username, String targetLocation) {
        findAccountByUsername(username);
        return new NearByFriendsDto(accountRepository.findByNearLocation(username,targetLocation));
    }

    private Account findAccountByUsername(String name){
        return accountRepository
                .findAccountByUsername(name)
                .orElseThrow(()->{throw new GlobalException(
                        HttpStatus.NOT_FOUND,
                        ErrorType.NOT_FOUND_ERROR
                );});
    }

}
