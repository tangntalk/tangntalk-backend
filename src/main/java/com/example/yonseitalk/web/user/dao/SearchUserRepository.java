package com.example.yonseitalk.web.user.dao;

import java.util.List;

public interface SearchUserRepository {
    List<SearchUser> search(String id, String searchQuery);
}
