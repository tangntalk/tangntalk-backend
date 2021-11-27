package com.example.yonseitalk.view.chatroom;

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

    public void addSingleChatroom(ChatroomDetail chatroomDetail, String userId){
        chatrooms.add(SingleChatroom.builder()
                .chatroom_id(chatroomDetail.getChatroom_id())
                .last_message(chatroomDetail.getContent())
                .last_send_time(chatroomDetail.getSend_time())
                .last_message_from(chatroomDetail.getSender_id())
                .opponent_name((String.valueOf(chatroomDetail.getUser_1()).equals(userId)) ? chatroomDetail.getUser_2() : chatroomDetail.getUser_1())
                .message_location(chatroomDetail.getRendezvous_location())
                .rendezvous_time(chatroomDetail.getRendezvous_time())
                .build());
    }

    @Getter
    @Setter
    @Builder
    static class SingleChatroom {
        Long chatroom_id;
        String last_message;
        Timestamp last_send_time;
        String last_message_from;
        String opponent_name;
        String message_location;
        Timestamp rendezvous_time;
    }

}
