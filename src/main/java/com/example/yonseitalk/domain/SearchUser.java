package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchUser{
    private String user_id;
    private String name;
    private String status_message;
    private String type;
    private Boolean isFriend;

}
