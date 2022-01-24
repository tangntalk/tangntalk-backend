package com.example.yonseitalk.web.chatroom.dto;

import com.example.yonseitalk.web.chatroom.dao.ChatroomDetail;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatroomDto extends DefaultResponse{

    private List<SingleChatroom> chatrooms = new ArrayList<>();

    public void addSingleChatroom(ChatroomDetail chatroomDetail, String userName, String opponentId){
        chatrooms.add(SingleChatroom.builder()
                .chatroom_id(chatroomDetail.getChatroomId())
                .last_message(chatroomDetail.getContent())
                .last_send_time(String.valueOf(chatroomDetail.getSendTime()))
                .last_message_from(chatroomDetail.getSenderId())
                .opponent_name((String.valueOf(chatroomDetail.getUser1()).equals(userName)) ? chatroomDetail.getUser2() : chatroomDetail.getUser1())
                .opponent_id(opponentId)
                .message_location(chatroomDetail.getRendezvousLocation())
                .rendezvous_time(String.valueOf(chatroomDetail.getRendezvousTime()))
                .connection_Status(chatroomDetail.getConnectionStatus())
                .build());
    }

    @Getter
    @Setter
    @Builder
    static class SingleChatroom {
        Long chatroom_id;
        String last_message;
        String last_send_time;
        String last_message_from;
        String opponent_name;
        String opponent_id;
        String message_location;
        String rendezvous_time;
        Boolean connection_Status;
    }

}
