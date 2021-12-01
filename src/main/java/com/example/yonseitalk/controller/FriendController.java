package com.example.yonseitalk.controller;


import com.example.yonseitalk.domain.FriendUser;
import com.example.yonseitalk.domain.SearchUser;
import com.example.yonseitalk.service.FriendService;
import com.example.yonseitalk.service.SearchService;
import com.example.yonseitalk.view.DefaultResponse;
import com.example.yonseitalk.view.friend.FriendCheckView;
import com.example.yonseitalk.view.friend.FriendListView;
import com.example.yonseitalk.view.friend.SearchFriendView;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/users")
@RestController
public class FriendController {
    private final FriendService friendService;
    private final SearchService searchService;

    public FriendController(FriendService friendService, SearchService searchService) {
        this.friendService = friendService;
        this.searchService = searchService;
    }

    @GetMapping("/{user_id}/friends")
    public FriendListView friendList(@PathVariable("user_id") String userId){
        List<FriendUser> friendUsers = friendService.FindFriends(userId);
        FriendListView friendListView = new FriendListView();
        if (!friendUsers.isEmpty()){
            friendUsers.forEach(friendListView::addUser);
        }
        friendListView.setSuccess(true);
        return friendListView;
    }

    @GetMapping("/{user_id}/friends/search")
    public SearchFriendView friendList(@PathVariable("user_id") String userId, @RequestParam("query") String query){
        List<SearchUser> searchUsers = searchService.searchFriends(userId, query);
        SearchFriendView searchFriendView = new SearchFriendView();
        if (!searchUsers.isEmpty()){
            searchUsers.forEach(searchFriendView::addUser);
        }
        searchFriendView.setSuccess(true);
        return searchFriendView;
    }

    @PostMapping("/{user_id}/friends")
    public DefaultResponse addFriend(@PathVariable("user_id") String userId, @RequestBody Map<String, String> friendId ){
        int status = friendService.addFriend(userId,friendId.get("friend_id"));
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(status > 0);
        return defaultResponse;
    }

    @DeleteMapping("/{user_id}/friends/{friend_id}")
    public DefaultResponse delFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        int status = friendService.delFriend(userId,friendId);
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(status > 0);
        return defaultResponse;
    }

    @GetMapping("/{user_id}/friends/{friend_id}")
    public DefaultResponse isFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        boolean isFriend = friendService.isFriend(userId,friendId);
        FriendCheckView friendCheckView = new FriendCheckView();
        friendCheckView.setSuccess(true);
        friendCheckView.set_friend(isFriend);

        return friendCheckView;
    }

}

