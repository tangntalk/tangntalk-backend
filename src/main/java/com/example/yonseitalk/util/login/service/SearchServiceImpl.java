package com.example.yonseitalk.util.login.service;

import com.example.yonseitalk.web.user.dao.DBSearchUserRepository;
import com.example.yonseitalk.web.user.dao.SearchUser;
import com.example.yonseitalk.web.user.dao.SearchUserRepository;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchUserRepository searchUserRepository;

    @Override
    public List<SearchUser> searchFriends(String userId, String searchQuery) {
        return searchUserRepository.search(userId,searchQuery);
    }

    @Override
    public String queryValidateTransform(String searchQuery) {
        return null;
    }
}
