package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Message {
    private Long message_id;
    private Long chatroom_id;
    private String sender_id;
    private String receiver_id;
    private String content;
    private Timestamp send_time;
    private Timestamp read_time;
    private Boolean rendezvous_flag;
    private String rendezvous_location;
    private Timestamp rendezvous_time;

}
