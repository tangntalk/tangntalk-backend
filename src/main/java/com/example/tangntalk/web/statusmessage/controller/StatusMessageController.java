package com.example.tangntalk.web.statusmessage.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.account.dto.response.AccountInfoDto;
import com.example.tangntalk.web.account.service.AccountService;
import com.example.tangntalk.web.statusmessage.domain.StatusMessage;
import com.example.tangntalk.web.statusmessage.service.StatusMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class StatusMessageController {
    private final StatusMessageService statusMessageService;

    @PutMapping
    public Response.Item<AccountInfoDto> updateStatusMessage(){


    }

    @GetMapping
    public Response.Item<AccountInfoDto> getStatusFeed(){

    }

}
