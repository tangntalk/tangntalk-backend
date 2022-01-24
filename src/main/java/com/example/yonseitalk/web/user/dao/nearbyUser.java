package com.example.yonseitalk.web.user.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class nearbyUser {

    private String name;
    private String userId;
    private String statusMessage;
    private String type;
    private Long chatroomId;
}
