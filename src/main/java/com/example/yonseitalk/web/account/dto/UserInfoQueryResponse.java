package com.example.yonseitalk.web.user.dto;

import com.example.yonseitalk.web.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoQueryResponse {
    String name;
    String statusMessage;
    String locationName;

    public static UserInfoQueryResponse fromUser(User user){
        return UserInfoQueryResponse.builder()
                .name(user.getName())
                .statusMessage(user.getStatusMessage())
                .locationName(user.getUserLocation())
                .build();
    }

    public UserInfoQueryResponse toUser(){
        return UserInfoQueryResponse.builder()
                .name(name)
                .statusMessage(statusMessage)
                .locationName(locationName)
                .build();
    }
}
