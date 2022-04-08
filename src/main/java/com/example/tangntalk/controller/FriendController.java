package com.example.tangntalk.controller;


import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.FriendDto;

import com.example.tangntalk.web.account.dto.OnlineAndOfflineFriendListDto;
import com.example.tangntalk.web.account.dto.FriendSearchResponse;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping("/friends")
    public Response.Item<OnlineAndOfflineFriendListDto> friendList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.findFriendAccount(username));
    }

    @GetMapping("/friends/search")
    public Response.ItemList<FriendSearchResponse> friendList(@RequestParam("query") String query){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.ItemList<>(accountService.searchByNameOrUsername(username,query));
    }

    @PostMapping("/friends")
    public Response.Empty addFriend(@RequestBody FriendDto.Request.addFriend requestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.addFriend(username, requestDto.getFriendUsername());
        return new Response.Empty();
    }

    @DeleteMapping("/friends/{friendUsername}")
    public Response.Empty delFriend(@PathVariable("friendUsername") String friendUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.deleteFriend(username,friendUsername);
        return new Response.Empty();
    }

    @GetMapping("/friends/{friendUsername}")
    public Response.Item<FriendDto.Response.FriendCheck> isFriend(@PathVariable("friendUsername") String friendUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.isFriend(username,friendUsername));
    }

}

