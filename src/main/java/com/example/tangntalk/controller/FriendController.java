package com.example.tangntalk.controller;


import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.FriendDto;

import com.example.tangntalk.web.account.dto.OnlineAndOfflineFriendListDto;
import com.example.tangntalk.web.account.dto.FriendSearchResponse;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping("/{username}/friends")
    public Response.Item<OnlineAndOfflineFriendListDto> friendList(@PathVariable("username") String username){
        return new Response.Item<>(accountService.findFriendAccount(username));
    }

    @GetMapping("/{username}/friends/search")
    public Response.ItemList<FriendSearchResponse> friendList(
            @PathVariable("username") String username,
            @RequestParam("query") String query){
        return new Response.ItemList<>(accountService.searchByNameOrUsername(username,query));
    }

    @PostMapping("/{username}/friends")
    public Response.Empty addFriend(@PathVariable("username") String username, @RequestBody FriendDto.Request.addFriend requestDto){
        accountService.addFriend(username, requestDto.getFriendId());
        return new Response.Empty();
    }

    @DeleteMapping("/{username}/friends/{friendId}")
    public Response.Empty delFriend(@PathVariable("username") String username, @PathVariable("friendId") String friendId){
        accountService.deleteFriend(username,friendId);
        return new Response.Empty();
    }

    @GetMapping("/{username}/friends/{friendId}")
    public Response.Item<FriendDto.Response.FriendCheck> isFriend(@PathVariable("username") String username, @PathVariable("friendId") String friendId){
        return new Response.Item<>(accountService.isFriend(username,friendId));
    }

}

