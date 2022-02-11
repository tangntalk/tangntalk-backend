package com.example.yonseitalk.web.friend.dto;

import com.example.yonseitalk.web.account.dto.SearchAccount;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchFriendView extends DefaultResponse {
    private List<SearchAccount> user = new ArrayList<>();
    public void addUser(SearchAccount searchAccount){
        user.add(searchAccount);
    }
}
