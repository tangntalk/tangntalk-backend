package com.example.yonseitalk.service;

import com.example.yonseitalk.domain.SearchUser;
import com.example.yonseitalk.repository.SearchUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{
    private final SearchUserRepository searchUserRepository;

    @Autowired
    public SearchServiceImpl(SearchUserRepository searchUserRepository) {
        this.searchUserRepository = searchUserRepository;
    }

    @Override
    public List<SearchUser> searchFriends(String userId, String searchQuery) {
        return searchUserRepository.search(userId,searchQuery);
    }

    @Override
    public String queryValidateTransform(String searchQuery) {
        return null;
    }
}
