package com.example.yonseitalk.controller;


import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.account.dto.FriendDto;

import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping("/{user_id}/friends")
    public Response.Item<FriendDto.Response.FriendQuery> friendList(@PathVariable("user_id") String userId){
        return new Response.Item<>(accountService.findFriendAccount(userId));
    }

    @GetMapping("/{user_id}/friends/search")
    public Response.ItemList<FriendDto.Response.SearchFriend> friendList(
            @PathVariable("user_id") String userId,
            @RequestParam("query") String query){
        return new Response.ItemList<>(accountService.search(userId,query));
    }

    @PostMapping("/{user_id}/friends")
    public Response.Empty addFriend(@PathVariable("user_id") String userId, @RequestBody Map<String, String> friendId ){
        accountService.addFriend(userId,friendId.get("friend_id"));
        return new Response.Empty();
    }

    @DeleteMapping("/{user_id}/friends/{friend_id}")
    public Response.Empty delFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        accountService.delFriend(userId,friendId);
        return new Response.Empty();
    }

    @GetMapping("/{user_id}/friends/{friend_id}")
    public Response.Item<FriendDto.Response.FriendCheck> isFriend(@PathVariable("user_id") String userId, @PathVariable("friend_id") String friendId){
        return new Response.Item<>(accountService.isFriend(userId,friendId));
    }

}

