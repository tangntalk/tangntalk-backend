package com.example.tangntalk.web.account.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.request.ModifyInfoDto;
import com.example.tangntalk.web.account.dto.response.AccountInfoDto;
import com.example.tangntalk.web.account.dto.response.NearByFriendsDto;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class AccountInfoController {

    private final AccountService accountService;

    @GetMapping
    public Response.Item<AccountInfoDto> accountInfo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.getAccountInfo(username));
    }

    @PatchMapping(value = "/myinfo")
    public Response.Empty modifyInformation(@RequestBody ModifyInfoDto modifyInfoDto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.modifyInformation(username , modifyInfoDto);
        return new Response.Empty();
    }

    @DeleteMapping
    public Response.Empty deleteUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.deleteById(username);
        return new Response.Empty();
    }

    //nearby
    @GetMapping(value = "/nearby/{targetLocation}")
    public Response.Item<NearByFriendsDto> nearbyUser(@PathVariable("targetLocation") String target_location){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(accountService.nearByQuery(username,target_location));
    }
}
