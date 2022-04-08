package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class AccountRegisterController {

    private final AccountService accountService;

    @PostMapping
    public Response.Empty register(@RequestBody AccountDto.Request.Register accountRegisterRequest){
        accountService.save(accountRegisterRequest);
        return new Response.Empty();
    }

}
