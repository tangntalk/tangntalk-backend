package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    private String name;
    private String user_id;
    private String password;
    private String status_message;
    private String type;
    private String user_location;
    private Boolean connection_status;
}
