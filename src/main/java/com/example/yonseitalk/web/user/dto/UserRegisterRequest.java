package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String userId;
    private String name;
    private String password;
    private String type;

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .name(name)
                .password(password)
                .type(type)
                .build();
    }
}
