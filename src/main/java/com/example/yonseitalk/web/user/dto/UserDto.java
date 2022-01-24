package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.user.dao.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String statusMessage;
    private String type;
    private String userLocation;
    private Boolean connectionStatus;

    public static UserDto fromUser(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .statusMessage(user.getStatusMessage())
                .type(user.getType())
                .userLocation(user.getUserLocation())
                .connectionStatus(user.getConnectionStatus())
                .build();
    }

    public User toUser(){
        return User.builder()
                .userId(getUserId())
                .name(getName())
                .email(getEmail())
                .password(getPassword())
                .role(getRole())
                .statusMessage(getStatusMessage())
                .type(getType())
                .userLocation(getUserLocation())
                .connectionStatus(getConnectionStatus())
                .build();
    }
}
