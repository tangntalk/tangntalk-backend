package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.web.user.dto.SearchUser;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final UserService userService;

    @Override
    public List<SearchUser> searchFriends(String userId, String searchQuery) {
        return userService.search(userId,searchQuery);
    }

    @Override
    public String queryValidateTransform(String searchQuery) {
        return null;
    }
}
