package com.example.tangntalk.web.statusmessage.service;

import com.example.tangntalk.web.account.repository.AccountRepository;
import com.example.tangntalk.web.statusmessage.repository.StatusMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusMessageService {
    private final StatusMessageRepository statusMessageRepository;
    private final AccountRepository accountRepository;



}
