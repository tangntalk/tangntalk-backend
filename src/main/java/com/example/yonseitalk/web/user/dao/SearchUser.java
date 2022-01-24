package com.example.yonseitalk.web.user.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchUser{
    private String userId;
    private String name;
    private String statusMessage;
    private String type;
    private Boolean isFriend;

}
