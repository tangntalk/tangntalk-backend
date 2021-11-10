package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Chatroom {

    private Long chatroom_id;
    private String user_1;
    private String user_2;
    private String talk;
    private Timestamp last_send_time;
    private String last_send_user;

}
