package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUser{
    private String user_id;
    private String name;
    private String status_message;
    private String type;
    private Boolean isFriend;

    @Override
    public String toString() {
        return "SearchUser{" +
                "name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", status_message='" + status_message + '\'' +
                ", type='" + type + '\'' +
                ", isFriend=" + isFriend +
                '}';
    }
}
