package com.example.yonseitalk.repository;

import com.example.yonseitalk.domain.SearchUser;

import java.util.List;

public interface SearchUserRepository {
    List<SearchUser> search(String id, String searchQuery);
}
