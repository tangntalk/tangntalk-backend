package com.example.tangntalk.web.account.dto.projection;

public interface NearByAccountProjection {
    String getName();
    String getUsername();
    String getStatusMessage();
    String getType();
    Boolean getConnectionStatus();
    String getAccountLocation();
    Long getChatroomId();
}
