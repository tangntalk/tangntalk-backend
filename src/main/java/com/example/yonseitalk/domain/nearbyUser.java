package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class nearbyUser {

    private String name;
    private String user_id;
    private String status_message;
    private String type;
    private Long chatroom_id;
}
