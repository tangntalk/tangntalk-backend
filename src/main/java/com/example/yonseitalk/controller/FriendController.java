package com.example.yonseitalk.controller;


import com.example.yonseitalk.web.account.dto.FriendAccount;
import com.example.yonseitalk.web.account.dto.SearchAccount;
import com.example.yonseitalk.view.DefaultResponse;
import com.example.yonseitalk.web.friend.dto.FriendCheckView;
import com.example.yonseitalk.web.friend.dto.FriendListView;
import com.example.yonseitalk.web.friend.dto.SearchFriendView;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping("/{user_id}/friends")
    public FriendListView friendList(@PathVariable("user_id") String userId){
        List<FriendAccount> friendAccounts = accountService.findFriendAccount(userId);
        FriendListView friendListView = new FriendListView();
        if (!friendAccounts.isEmpty()){
            friendAccounts.forEach(friendListView::addUser);
        }
        friendListView.setSuccess(true);
        return friendListView;
    }

    @GetMapping("/{user_id}/friends/search")
    public SearchFriendView friendList(@PathVariable("user_id") String userId, @RequestParam("query") String query){
        List<SearchAccount> searchAccounts = accountService.search(userId, query);
        SearchFriendView searchFriendView = new SearchFriendView();
        if (!searchAccounts.isEmpty()){
            searchAccounts.forEach(searchFriendView::addUser);
        }
        searchFriendView.setSuccess(true);
        return searchFriendView;
    }

    @PostMapping("/{user_id}/friends")
    public DefaultResponse addFriend(@PathVariable("user_id") String userId, @RequestBody Map<String, String> friendId ){
        accountService.addFriend(userId,friendId.get("friend_id"));
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        return defaultResponse;
    }

    @DeleteMapping("/{user_id}/friends/{friend_id}")
    public DefaultResponse delFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        accountService.delFriend(userId,friendId);
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        return defaultResponse;
    }

    @GetMapping("/{user_id}/friends/{friend_id}")
    public DefaultResponse isFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        boolean isFriend = accountService.isFriend(userId,friendId);
        FriendCheckView friendCheckView = new FriendCheckView();
        friendCheckView.setSuccess(true);
        friendCheckView.set_friend(isFriend);

        return friendCheckView;
    }

}

