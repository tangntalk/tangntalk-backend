package com.example.yonseitalk.web.account.service;

import com.example.yonseitalk.exception.DuplicateAccountException;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.security.authorization.role.AccountRole;
import com.example.yonseitalk.web.account.domain.AccountQdslRepository;
import com.example.yonseitalk.web.account.dto.*;
import com.example.yonseitalk.web.account.domain.Account;
import com.example.yonseitalk.web.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountQdslRepository accountQdslRepository;

    @Override
    @Transactional
    public void save(AccountDto.Request.Register accountRegisterRequest) {
        accountRepository.findById(accountRegisterRequest.getAccountId())
                .ifPresent(user -> {throw new DuplicateAccountException();});
//        accountRegisterRequest.setPassword(AES128.getAES128_Encode(accountRegisterRequest.getPassword()));
        Account account = accountRegisterRequest.toEntity();
        account.setConnectionStatus(false);
        account.setStatusMessage("");
        account.setAccountLocation("공학관");
        account.setRole("학생");
        accountRepository.save(account);

    }

    @Override
    public void save(AccountDto accountDto) {
        accountRepository.save(accountDto.toAccount());
    }

    @Override
    public AccountInfoQueryResponse accountInfoQuery(String id) {
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
    public List<Account> findByLocation(String location){
        return accountRepository.findByAccountLocation(location);
    }

    @Transactional
    public void modifyInformation(String id, AccountDto.Request.ModifyInfo modifyInfo) {
        Account account = accountRepository.findById(id).orElseThrow(NotFoundException::new);
        if (modifyInfo.getAccountLocation()!=null){
            account.setAccountLocation(modifyInfo.getAccountLocation());
        }
        if (modifyInfo.getStatusMessage()!=null){
            account.setStatusMessage(modifyInfo.getStatusMessage());
        }
    }

    @Transactional
    public int updateAccountConnectionStatus(String id, Boolean flag){
        Optional<Account> userOptional = accountRepository.findById(id);

        userOptional.ifPresent(user -> user.setConnectionStatus(flag));

        return userOptional.isPresent()?1:0;
    }


    @Transactional
    public FriendQueryResponse findFriendAccount(String accountId){
        Account requestAccount = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        return FriendQueryResponse.fromFriendDtoList(accountQdslRepository.friendQuery(requestAccount));
    }

    @Transactional
    public void addFriend(String userId, String friendId){
        Account user = accountRepository.findById(userId).orElseThrow(NotFoundException::new);
        Account friend = accountRepository.findById(friendId).orElseThrow(NotFoundException::new);
        user.getAccountAddedFriends().add(friend);
        friend.getFriendsAddedAccount().add(user);
    }

    @Transactional
    public void delFriend(String userId, String friendId){
        Account user = accountRepository.findById(userId).orElseThrow(NotFoundException::new);
        Account friend = accountRepository.findById(friendId).orElseThrow(NotFoundException::new);
        user.getAccountAddedFriends().remove(friend);
        friend.getFriendsAddedAccount().remove(user);
    }

    @Transactional
    public FriendDto.Response.FriendCheck isFriend(String accountId, String friendId){
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        Account friend = accountRepository.findById(friendId).orElseThrow(NotFoundException::new);
        return new FriendDto.Response.FriendCheck(account.getAccountAddedFriends().contains(friend));
    }

    @Transactional
    public List<FriendSearchResponse> search(String accountId, String searchQuery){
        Account requestAccount = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);

        List<Account> searchAccountList = accountQdslRepository.search(accountId,searchQuery);
        Set<Account> friendList = accountRepository.findByFriendsAddedAccountContains(requestAccount);

        return searchAccountList
                .stream()
                .map(account -> {
                    return FriendSearchResponse
                            .fromAccount(account,friendList.contains(account));
                })
                .collect(Collectors.toList());

    }

    @Override
    public AccountDto.Response.NearBy nearByQuery(String accountId,String targetLocation) {
        accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        return new AccountDto.Response.NearBy(accountRepository.findByNearLocation(accountId,targetLocation));
    }

}
