package com.example.yonseitalk.web.message.dto;

import com.example.yonseitalk.web.message.dao.Message;
import com.example.yonseitalk.view.DefaultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MessageListView extends DefaultResponse {

    private List<SingleMessage> messages = new ArrayList<>();

    public void addSingleMessage(Message message){
        messages.add(SingleMessage.builder()
                .message_id(message.getMessageId())
                .chatroom_id(message.getChatroomId())
                .sender_id(message.getSenderId())
                .content(message.getContent())
                .send_time(String.valueOf(message.getSendTime()))
                .read_time((message.getReadTime() == null) ? "읽지 않음" : "읽음")
                .rendezvous_flag(message.getRendezvousFlag())
                .rendezvous_location(message.getRendezvousLocation())
                .rendezvous_time((message.getRendezvousTime() == null) ? null : String.valueOf(message.getRendezvousTime()))
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
