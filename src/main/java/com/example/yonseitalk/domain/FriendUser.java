package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendUser extends User{
    public FriendUser() {
        super.setPassword(null);
    }

    private Boolean isFriend;

    @Override
    public String toString() {

        return "FriendUser{" +super.toString()+
                ", isFriend=" + isFriend +
                '}';
    }
}
