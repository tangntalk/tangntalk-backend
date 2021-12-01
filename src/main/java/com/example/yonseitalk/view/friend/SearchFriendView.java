package com.example.yonseitalk.view.friend;

import com.example.yonseitalk.domain.SearchUser;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchFriendView extends DefaultResponse {
    private List<SearchUser> user = new ArrayList<>();
    public void addUser(SearchUser searchUser){
        user.add(searchUser);
    }
}
