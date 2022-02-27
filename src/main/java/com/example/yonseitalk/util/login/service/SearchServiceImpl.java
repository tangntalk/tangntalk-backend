package com.example.yonseitalk.util.login.service;


import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final AccountService accountService;

//    @Override
//    public List<SearchAccount> searchFriends(String userId, String searchQuery) {
//        return accountService.search(userId,searchQuery);
//    }

    @Override
    public String queryValidateTransform(String searchQuery) {
        return null;
    }
}
