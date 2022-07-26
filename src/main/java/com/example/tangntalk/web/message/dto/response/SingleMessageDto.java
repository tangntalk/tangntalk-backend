package com.example.tangntalk.web.message.dto.response;

import com.example.tangntalk.web.message.dto.MessageDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleMessageDto {
    Long messageId;
    Long chatroomId;
    String senderId;
    String content;
    String sendTime;
    String readTime;
    Boolean rendezvousFlag;
    String rendezvousLocation;
    String rendezvousTime;


    public static SingleMessageDto from(MessageDto messageDto){
        return SingleMessageDto.builder()
                .messageId(messageDto.getMessageId())
                .chatroomId(messageDto.getChatroomId())
                .senderId(messageDto.getSenderId())
                .content(messageDto.getContent())
                .sendTime(String.valueOf(messageDto.getSendTime()))
                .readTime((messageDto.getReadTime() == null) ? "읽지 않음" : "읽음")
                .rendezvousFlag(messageDto.getRendezvousFlag())
                .rendezvousLocation(messageDto.getRendezvousLocation())
                .rendezvousTime((messageDto.getRendezvousTime() == null) ? null : String.valueOf(messageDto.getRendezvousTime()))
                .build();
    }
}
