package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.dto.AccountRegisterRequest;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/register")
public class AccountRegisterController {

    private final AccountService accountService;

    @GetMapping("")
    public void enterRegister(){
        //프론트에서 알아서 함.
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Response.Item<AccountDto> register(@RequestBody AccountRegisterRequest accountRegisterRequest){
        return new Response.Item<>(accountService.save(accountRegisterRequest));
    }

}