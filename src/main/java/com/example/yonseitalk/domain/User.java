package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User {

    private String name;
    private String user_id;
    private String password;
    private Timestamp user_time;
    private String status_message;
    private String type;
    private String user_location;
}
