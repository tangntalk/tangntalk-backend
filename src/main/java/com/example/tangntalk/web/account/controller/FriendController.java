package com.example.tangntalk.web.account.controller;


import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.response.AccountInfoDto;
import com.example.tangntalk.web.account.dto.response.FriendDto;

import com.example.tangntalk.web.account.dto.response.OnlineAndOfflineFriendListDto;
import com.example.tangntalk.web.account.dto.response.FriendSearchDto;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accounts/friends")
@RestController
@RequiredArgsConstructor
public class FriendController {

    private final AccountService accountService;

    @GetMapping
    public Response.Item<OnlineAndOfflineFriendListDto> friendList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.findFriendAccount(username));
    }

    @GetMapping("/search")
    public Response.ItemList<FriendSearchDto> friendList(@RequestParam("query") String query){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.ItemList<>(accountService.searchByNameOrUsername(username,query));
    }

    @PostMapping
    public Response.Empty addFriend(@RequestBody FriendDto.Request.addFriend requestDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.addFriend(username, requestDto.getFriendUsername());
        return new Response.Empty();
    }

    @DeleteMapping("/{friendUsername}")
    public Response.Empty delFriend(@PathVariable("friendUsername") String friendUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.deleteFriend(username,friendUsername);
        return new Response.Empty();
    }

    @GetMapping("/{friendUsername}")
    public Response.Item<AccountInfoDto> friendInfo(@PathVariable("friendUsername") String friendUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.friendInfo(username,friendUsername));
    }

    @GetMapping("/isFriend/{friendUsername}")
    public Response.Item<FriendDto.Response.FriendCheck> isFriend(@PathVariable("friendUsername") String friendUsername){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.isFriend(username,friendUsername));
    }
}

