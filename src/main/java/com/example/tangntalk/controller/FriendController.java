package com.example.tangntalk.controller;


import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.FriendDto;

import com.example.tangntalk.web.account.dto.FriendQueryResponse;
import com.example.tangntalk.web.account.dto.FriendSearchResponse;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping("/{accountId}/friends")
    public Response.Item<FriendQueryResponse> friendList(@PathVariable("accountId") String accountId){
        return new Response.Item<>(accountService.findFriendAccount(accountId));
    }

    @GetMapping("/{accountId}/friends/search")
    public Response.ItemList<FriendSearchResponse> friendList(
            @PathVariable("accountId") String accountId,
            @RequestParam("query") String query){
        return new Response.ItemList<>(accountService.search(accountId,query));
    }

    @PostMapping("/{accountId}/friends")
    public Response.Empty addFriend(@PathVariable("accountId") String accountId, @RequestBody FriendDto.Request.addFriend requestDto){
        accountService.addFriend(accountId, requestDto.getFriendId());
        return new Response.Empty();
    }

    @DeleteMapping("/{accountId}/friends/{friendId}")
    public Response.Empty delFriend(@PathVariable("accountId") String accountId, @PathVariable("friendId") String friendId){
        accountService.delFriend(accountId,friendId);
        return new Response.Empty();
    }

    @GetMapping("/{accountId}/friends/{friendId}")
    public Response.Item<FriendDto.Response.FriendCheck> isFriend(@PathVariable("accountId") String accountId, @PathVariable("friendId") String friendId){
        return new Response.Item<>(accountService.isFriend(accountId,friendId));
    }

}

