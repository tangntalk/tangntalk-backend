package com.example.tangntalk.web.account.dto.projection;

public interface FriendAccountProjection {
    String getName();
    String getUsername();
    String getStatusMessage();
    String getType();
    Boolean getConnectionStatus();
    String getAccountLocation();
    Long getChatroomId();
}
