package com.example.yonseitalk.web.friend.dto;


import com.example.yonseitalk.web.user.dto.FriendUser;
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


    public void addUser(FriendUser friendUser){
        InnerUser innerUser = new InnerUser();
        innerUser.setUserId(friendUser.getUserId());
        innerUser.setName(friendUser.getName());
        innerUser.setStatusMessage(friendUser.getStatusMessage());
        innerUser.setType(friendUser.getType());
        innerUser.setChatroomId(friendUser.getChatroomId());
        innerUser.setUserLocation(friendUser.getUserLocation());
        if(friendUser.getConnectionStatus()){
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
