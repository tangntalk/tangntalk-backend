package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.user.dao.User;
import lombok.*;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
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
                .userId(userId)
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .statusMessage(statusMessage)
                .type(type)
                .userLocation(userLocation)
                .connectionStatus(connectionStatus)
                .build();
    }
}
