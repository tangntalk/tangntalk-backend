package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@Entity
public class FriendUser{
    private String name;
    private String user_id;
    private String status_message;
    private String type;
    private Boolean connection_status;
    private String user_location;
    private Long chatroomId;
}
