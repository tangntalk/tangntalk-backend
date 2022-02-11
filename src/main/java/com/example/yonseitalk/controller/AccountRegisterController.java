package com.example.yonseitalk.controller;

import com.example.yonseitalk.web.account.dto.AccountDto;
import com.example.yonseitalk.web.account.dto.AccountRegisterRequest;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @PostMapping("")
    public void register(@RequestBody AccountRegisterRequest accountRegisterRequest, HttpServletResponse response) throws IOException {

        if(accountService.findById(accountRegisterRequest.getAccountId()).isEmpty()){
            AccountDto accountDto = accountService.save(accountRegisterRequest);
            log.info("User={}", accountDto);
            //true로 응답을 줘라.(이건 일단 이대로 리다이렉팅으로 하자)
            response.setStatus(201);
        }
        else{
            response.setStatus(401);
        }

    }

}
