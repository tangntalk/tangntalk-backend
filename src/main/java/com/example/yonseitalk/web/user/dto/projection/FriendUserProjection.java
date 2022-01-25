package com.example.yonseitalk.web.user.dto.projection;

public interface FriendUserProjection {
    String getName();
    String getUserId();
    String getStatusMessage();
    String getType();
    Boolean getConnectionStatus();
    String getUserLocation();
    Long getChatroomId();
}
