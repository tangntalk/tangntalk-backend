package com.example.tangntalk.web.account.dto.projection;

public interface NearByAccountProjection {
    String getName();
    String getAccountId();
    String getStatusMessage();
    String getType();
    Boolean getConnectionStatus();
    String getAccountLocation();
    Long getChatroomId();
}
