package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.account.dto.*;
import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AccountInfoController {

    private final AccountService accountService;
    private final ChatroomRepository chatroomRepository;

    @GetMapping(value = "/{user_id}")
    public Response.Item<AccountInfoQueryResponse> accountInfo(@PathVariable("user_id") String accountId){
        return new Response.Item<>(accountService.accountInfoQuery(accountId));
    }

    @PatchMapping(value = "/{user_id}")
    public Response.Empty modifyInformation(@PathVariable("user_id") String accountId,
                                                    @RequestBody AccountDto.Request.ModifyInfo modifyInfo){
        accountService.modifyInformation(accountId ,modifyInfo);
        return new Response.Empty();
    }

    @DeleteMapping(value = "/{user_id}")
    public Response.Empty deleteUser(@PathVariable("user_id") String userId){
        accountService.deleteById(userId);
        return new Response.Empty();
    }

    //nearby
    @GetMapping(value = "/{user_id}/nearby/{target_location}")
    public Response.Item<AccountDto.Response.NearBy> nearbyUser(@PathVariable("user_id") String userId, @PathVariable("target_location") String target_location){
        return new Response.Item<>(accountService.nearByQuery(userId,target_location));
    }
}
