package com.example.yonseitalk.controller;


import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.account.dto.FriendDto;

import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping("/{account_id}/friends")
    public Response.Item<FriendDto.Response.FriendQuery> friendList(@PathVariable("account_id") String accountId){
        return new Response.Item<>(accountService.findFriendAccount(accountId));
    }

    @GetMapping("/{account_id}/friends/search")
    public Response.ItemList<FriendDto.Response.SearchFriend> friendList(
            @PathVariable("account_id") String accountId,
            @RequestParam("query") String query){
        return new Response.ItemList<>(accountService.search(accountId,query));
    }

    @PostMapping("/{account_id}/friends")
    public Response.Empty addFriend(@PathVariable("account_id") String accountId, @RequestBody FriendDto.Request.addFriend requestDto){
        accountService.addFriend(accountId, requestDto.getFriendId());
        return new Response.Empty();
    }

    @DeleteMapping("/{account_id}/friends/{friend_id}")
    public Response.Empty delFriend(@PathVariable("account_id") String accountId, @PathVariable("friend_id") String friendId){
        accountService.delFriend(accountId,friendId);
        return new Response.Empty();
    }

    @GetMapping("/{account_id}/friends/{friend_id}")
    public Response.Item<FriendDto.Response.FriendCheck> isFriend(@PathVariable("account_id") String accountId, @PathVariable("friend_id") String friendId){
        return new Response.Item<>(accountService.isFriend(accountId,friendId));
    }

}

