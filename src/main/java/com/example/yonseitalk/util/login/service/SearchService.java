package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.web.account.dto.SearchAccount;

import java.util.List;

public interface SearchService {
    List<SearchAccount>searchFriends(String userId, String searchQuery);
    String queryValidateTransform(String searchQuery);
}
