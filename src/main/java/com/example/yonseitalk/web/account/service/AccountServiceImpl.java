package com.example.yonseitalk.web.account.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.exception.DuplicateAccountException;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.util.login.role.Role;
import com.example.yonseitalk.web.account.dto.*;
import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void save(AccountDto accountDto){
        accountRepository.save(accountDto.toAccount());
    }

    @Override
    @Transactional
    public AccountDto save(AccountRegisterRequest accountRegisterRequest) {
        accountRepository.findById(accountRegisterRequest.getAccountId())
                .ifPresent(user -> {throw new DuplicateAccountException();});
        accountRegisterRequest.setPassword(AES128.getAES128_Encode(accountRegisterRequest.getPassword()));
        Account account = accountRegisterRequest.toEntity();
        account.setConnectionStatus(false);
        account.setStatusMessage("");
        account.setAccountLocation("공학관");
        account.setRole(Role.USER.getValue());
        accountRepository.save(account);
        return AccountDto.fromAccount(account);
    }

    @Override
    public AccountInfoQueryResponse  accountInfoQuery(String id) {
        Account account = accountRepository.findById(id).orElseThrow(NotFoundException::new);
        return AccountInfoQueryResponse.fromAccount(account);
    }

    @Transactional
    public Optional<AccountDto> findById(String id){
        Account user = accountRepository.findById(id).orElse(null);
        return user==null?Optional.empty():Optional.of(AccountDto.fromAccount(user));
    }

    @Transactional
    public void deleteById(String id){
        accountRepository.deleteById(id);
    }

    @Transactional
    public List<AccountDto> findByLocation(String location){
        return accountRepository.findByLocation(location).stream().map(AccountDto::fromAccount).collect(Collectors.toList());
    }

    @Transactional
    public int updateStatusMessage(String id, String msg){
        Optional<Account> userOptional = accountRepository.findById(id);
        userOptional.ifPresent(user -> user.setStatusMessage(msg));
        return userOptional.isPresent()?1:0;
    }
    @Transactional
    public int updateAccountConnectionStatus(String id, Boolean flag){
        Optional<Account> userOptional = accountRepository.findById(id);

        userOptional.ifPresent(user -> user.setConnectionStatus(flag));

        return userOptional.isPresent()?1:0;
    }

    @Transactional
    public int updateAccountLocation(String id, String location){
        Optional<Account> userOptional = accountRepository.findById(id);

        userOptional.ifPresent(user -> user.setAccountLocation(location));

        return userOptional.isPresent()?1:0;
    }

    @Transactional
    public List<FriendAccount> findFriendAccount(String userId){
        return accountRepository.findAll(userId).stream().map(FriendAccount::fromProjection).collect(Collectors.toList());
    }

    @Transactional
    public void addFriend(String userId, String friendId){
        Account user = accountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        Account friend = accountRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("No user with id "+friendId));
        user.getAccountAddedFriends().add(friend);
        friend.getFriendsAddedAccount().add(user);
    }

    @Transactional
    public void delFriend(String userId, String friendId){
        Account user = accountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        Account friend = accountRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("No user with id "+friendId));
        user.getAccountAddedFriends().remove(friend);
        friend.getFriendsAddedAccount().remove(user);
    }

    @Transactional
    public boolean isFriend(String userId, String friendId){
        Account user = accountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        Account friend = accountRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("No user with id "+friendId));
        return user.getAccountAddedFriends().contains(friend);
    }

    @Transactional
    public List<SearchAccount> search(String userId, String searchQuery){
        return accountRepository.search(userId, searchQuery).stream().map(SearchAccount::fromProjection).collect(Collectors.toList());
    }

}
