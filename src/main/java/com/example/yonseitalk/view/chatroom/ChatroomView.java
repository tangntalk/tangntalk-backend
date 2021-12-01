package com.example.yonseitalk.view.chatroom;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.domain.ChatroomDetail;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatroomView extends DefaultResponse{

    private List<SingleChatroom> chatrooms = new ArrayList<>();

    public void addSingleChatroom(ChatroomDetail chatroomDetail, String userName, String opponentId){
        chatrooms.add(SingleChatroom.builder()
                .chatroom_id(chatroomDetail.getChatroom_id())
                .last_message(chatroomDetail.getContent())
                .last_send_time(String.valueOf(chatroomDetail.getSend_time()))
                .last_message_from(chatroomDetail.getSender_id())
                .opponent_name((String.valueOf(chatroomDetail.getUser_1()).equals(userName)) ? chatroomDetail.getUser_2() : chatroomDetail.getUser_1())
                .opponent_id(opponentId)
                .message_location(chatroomDetail.getRendezvous_location())
                .rendezvous_time(String.valueOf(chatroomDetail.getRendezvous_time()))
                .connection_Status(chatroomDetail.getConnection_status())
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
