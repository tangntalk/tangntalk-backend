package com.example.yonseitalk.controller;


import com.example.yonseitalk.web.user.dto.FriendUser;
import com.example.yonseitalk.web.user.dto.SearchUser;
import com.example.yonseitalk.view.DefaultResponse;
import com.example.yonseitalk.web.friend.dto.FriendCheckView;
import com.example.yonseitalk.web.friend.dto.FriendListView;
import com.example.yonseitalk.web.friend.dto.SearchFriendView;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;

    @GetMapping("/{user_id}/friends")
    public FriendListView friendList(@PathVariable("user_id") String userId){
        List<FriendUser> friendUsers = userService.findFriendUser(userId);
        FriendListView friendListView = new FriendListView();
        if (!friendUsers.isEmpty()){
            friendUsers.forEach(friendListView::addUser);
        }
        friendListView.setSuccess(true);
        return friendListView;
    }

    @GetMapping("/{user_id}/friends/search")
    public SearchFriendView friendList(@PathVariable("user_id") String userId, @RequestParam("query") String query){
        List<SearchUser> searchUsers = userService.search(userId, query);
        SearchFriendView searchFriendView = new SearchFriendView();
        if (!searchUsers.isEmpty()){
            searchUsers.forEach(searchFriendView::addUser);
        }
        searchFriendView.setSuccess(true);
        return searchFriendView;
    }

    @PostMapping("/{user_id}/friends")
    public DefaultResponse addFriend(@PathVariable("user_id") String userId, @RequestBody Map<String, String> friendId ){
        userService.addFriend(userId,friendId.get("friend_id"));
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        return defaultResponse;
    }

    @DeleteMapping("/{user_id}/friends/{friend_id}")
    public DefaultResponse delFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        userService.delFriend(userId,friendId);
        DefaultResponse defaultResponse = new DefaultResponse();
        defaultResponse.setSuccess(true);
        return defaultResponse;
    }

    @GetMapping("/{user_id}/friends/{friend_id}")
    public DefaultResponse isFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        boolean isFriend = userService.isFriend(userId,friendId);
        FriendCheckView friendCheckView = new FriendCheckView();
        friendCheckView.setSuccess(true);
        friendCheckView.set_friend(isFriend);

        return friendCheckView;
    }

}

