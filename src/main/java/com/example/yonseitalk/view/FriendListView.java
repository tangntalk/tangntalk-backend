package com.example.yonseitalk.view;


import com.example.yonseitalk.domain.FriendUser;
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
        innerUser.setUser_id(friendUser.getUser_id());
        innerUser.setName(friendUser.getName());
        innerUser.setStatus_message(friendUser.getStatus_message());
        innerUser.setType(friendUser.getType());
        innerUser.setChatroomId(friendUser.getChatroomId());
        innerUser.setUser_location(friendUser.getUser_location());
        if(friendUser.getConnection_status()){
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
        private String user_id;
        private String name;
        private String status_message;
        private String type;
        private String user_location;
        private Long chatroomId;
    }

}
