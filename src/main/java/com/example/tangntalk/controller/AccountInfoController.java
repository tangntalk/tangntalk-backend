package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.*;
import com.example.tangntalk.web.chatroom.domain.ChatroomRepository;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccountInfoController {

    private final AccountService accountService;
    private final ChatroomRepository chatroomRepository;

    @GetMapping(value = "/{username}")
    public Response.Item<AccountInfoDto> accountInfo(@PathVariable("username") String username){
        return new Response.Item<>(accountService.getAccountInfo(username));
    }

    @PatchMapping(value = "/{username}/myinfo")
    public Response.Empty modifyInformation(@PathVariable("username") String username,
                                                    @RequestBody AccountDto.Request.ModifyInfo modifyInfo){
        accountService.modifyInformation(username ,modifyInfo);
        return new Response.Empty();
    }

    @DeleteMapping(value = "/{username}")
    public Response.Empty deleteUser(@PathVariable("username") String username){
        accountService.deleteById(username);
        return new Response.Empty();
    }

    //nearby
    @GetMapping(value = "/{username}/nearby/{targetLocation}")
    public Response.Item<AccountDto.Response.NearBy> nearbyUser(@PathVariable("username") String username, @PathVariable("targetLocation") String target_location){
        return new Response.Item<>(accountService.nearByQuery(username,target_location));
    }
}
