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

    public void addSingleMessage(MessageDto messageDto){
        messages.add(SingleMessage.builder()
                .message_id(messageDto.getMessageId())
                .chatroom_id(messageDto.getChatroomId())
                .sender_id(messageDto.getSenderId())
                .content(messageDto.getContent())
                .send_time(String.valueOf(messageDto.getSendTime()))
                .read_time((messageDto.getReadTime() == null) ? "읽지 않음" : "읽음")
                .rendezvous_flag(messageDto.getRendezvousFlag())
                .rendezvous_location(messageDto.getRendezvousLocation())
                .rendezvous_time((messageDto.getRendezvousTime() == null) ? null : String.valueOf(messageDto.getRendezvousTime()))
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
