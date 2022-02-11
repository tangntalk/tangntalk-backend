package com.example.yonseitalk.web.friend.dto;


import com.example.yonseitalk.web.account.dto.FriendAccount;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FriendListView extends DefaultResponse {
    private List<InnerUser> online = new ArrayList<>();
    private List<InnerUser> offline = new ArrayList<>();


    public void addUser(FriendAccount friendAccount){
        InnerUser innerUser = new InnerUser();
        innerUser.setUserId(friendAccount.getAccountId());
        innerUser.setName(friendAccount.getName());
        innerUser.setStatusMessage(friendAccount.getStatusMessage());
        innerUser.setType(friendAccount.getType());
        innerUser.setChatroomId(friendAccount.getChatroomId());
        innerUser.setUserLocation(friendAccount.getAccountLocation());
        if(friendAccount.getConnectionStatus()){
            online.add(innerUser);
        }
        else{
            offline.add(innerUser);
        }
    }
    @Getter
    @Setter
    static
    class InnerUser{
        private String userId;
        private String name;
        private String statusMessage;
        private String type;
        private String userLocation;
        private Long chatroomId;
    }

}
