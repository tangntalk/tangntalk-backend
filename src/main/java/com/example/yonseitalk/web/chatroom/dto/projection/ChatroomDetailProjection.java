package com.example.yonseitalk.web.chatroom.dto.projection;

import java.sql.Timestamp;

public interface ChatroomDetailProjection {
    Long getChatroomId();
    String getUser1();
    String getUser2();
    String getSenderId();
    String getContent();
    Timestamp getSendTime();
    Boolean getRendezvousFlag();
    String getRendezvousLocation();
    Timestamp getRendezvousTime();
    Boolean getConnectionStatus();

}
