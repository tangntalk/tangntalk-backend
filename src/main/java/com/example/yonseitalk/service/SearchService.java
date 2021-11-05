package com.example.yonseitalk.service;

import com.example.yonseitalk.domain.SearchUser;

import java.util.List;

public interface SearchService {
    List<SearchUser>searchFriends(String userId, String searchQuery);
    String queryValidateTransform(String searchQuery);
}
