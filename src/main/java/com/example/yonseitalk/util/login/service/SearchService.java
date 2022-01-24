package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.web.user.dao.SearchUser;

import java.util.List;

public interface SearchService {
    List<SearchUser>searchFriends(String userId, String searchQuery);
    String queryValidateTransform(String searchQuery);
}
