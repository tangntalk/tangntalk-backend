package com.example.tangntalk.web.account.dto.projection;

public interface FriendAccountProjection {
    String getName();
    String getAccountId();
    String getStatusMessage();
    String getType();
    Boolean getConnectionStatus();
    String getAccountLocation();
    Long getChatroomId();
}
