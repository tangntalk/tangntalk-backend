package com.example.yonseitalk.domain.user;

import com.example.yonseitalk.domain.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class UserRegisterRequest {
    private String user_id;
    private String name;
    private String password;
    private String type;

    public User toEntity(){
        return User.builder()
                .userId(user_id)
                .name(name)
                .password(password)
                .type(type)
                .build();
    }
}
