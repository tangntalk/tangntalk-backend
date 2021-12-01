package com.example.yonseitalk.view.chatroom;

import com.example.yonseitalk.domain.ChatroomDetail;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MessageListView extends DefaultResponse {

    private List<SingleMessage> messages = new ArrayList<>();

    public void addSingleMessage(Message message){
        messages.add(SingleMessage.builder()
                .message_id(message.getMessage_id())
                .chatroom_id(message.getChatroom_id())
                .sender_id(message.getSender_id())
                .content(message.getContent())
                .send_time(String.valueOf(message.getSend_time()))
                .read_time((message.getRead_time() == null) ? "읽지 않음" : "읽음")
                .rendezvous_flag(message.getRendezvous_flag())
                .rendezvous_location(message.getRendezvous_location())
                .rendezvous_time((message.getRendezvous_time() == null) ? null : String.valueOf(message.getRendezvous_time()))
                .build());
    }

    @Getter
    @Setter
    @Builder
    static class SingleMessage {
        Long message_id;
        Long chatroom_id;
        String sender_id;
        String content;
        String send_time;
        String read_time;
        Boolean rendezvous_flag;
        String rendezvous_location;
        String rendezvous_time;
    }
}
