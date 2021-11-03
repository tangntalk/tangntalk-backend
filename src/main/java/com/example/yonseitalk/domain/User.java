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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", password='" + password + '\'' +
                ", status_message='" + status_message + '\'' +
                ", type='" + type + '\'' +
                ", user_location='" + user_location + '\'' +
                ", connection_status=" + connection_status +
                '}';
    }
}
