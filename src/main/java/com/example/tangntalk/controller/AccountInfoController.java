package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.*;
import com.example.tangntalk.web.chatroom.domain.Chatroom;
import com.example.tangntalk.exception.NotFoundException;
import com.example.tangntalk.web.chatroom.domain.ChatroomRepository;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccountInfoController {

    private final AccountService accountService;
    private final ChatroomRepository chatroomRepository;

    @GetMapping(value = "/{accountId}")
    public Response.Item<AccountInfoQueryResponse> accountInfo(@PathVariable("accountId") String accountId){
        return new Response.Item<>(accountService.accountInfoQuery(accountId));
    }

    @PatchMapping(value = "/{accountId}/myinfo")
    public Response.Empty modifyInformation(@PathVariable("accountId") String accountId,
                                                    @RequestBody AccountDto.Request.ModifyInfo modifyInfo){
        accountService.modifyInformation(accountId ,modifyInfo);
        return new Response.Empty();
    }

    @DeleteMapping(value = "/{accountId}")
    public Response.Empty deleteUser(@PathVariable("accountId") String accountId){
        accountService.deleteById(accountId);
        return new Response.Empty();
    }

    //nearby
    @GetMapping(value = "/{accountId}/nearby/{targetLocation}")
    public Response.Item<AccountDto.Response.NearBy> nearbyUser(@PathVariable("accountId") String accountId, @PathVariable("targetLocation") String target_location){
        return new Response.Item<>(accountService.nearByQuery(accountId,target_location));
    }
}
