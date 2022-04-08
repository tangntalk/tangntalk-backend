package com.example.tangntalk.web.message.dto;

import com.example.tangntalk.web.message.domain.Message;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class MessageDto {

    private Long messageId;
    private Long chatroomId;
    private String senderId;
    private String content;
    private Date sendTime;
    private Date readTime;
    private Boolean rendezvousFlag;
    private String rendezvousLocation;
    private Date rendezvousTime;

    public static MessageDto fromMessage(Message message){
        return MessageDto.builder()
                .messageId(message.getMessageId())
                .chatroomId(message.getChatroom().getChatroomId())
                .senderId(message.getSender().getUsername())
                .content(message.getContent())
                .sendTime(message.getSendTime())
                .readTime(message.getReadTime())
                .rendezvousFlag(message.getRendezvousFlag())
                .rendezvousLocation(message.getRendezvousLocation())
                .rendezvousTime(message.getRendezvousTime())
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class MessageCount {
        Long messageCount;
    }

    @Data
    public static class MessageList{
        List<SingleMessage> messageList = new ArrayList<>();

        public static MessageList fromMessageDtoList(List<MessageDto> messageDtos){
            MessageList messageList = new MessageList();

            for(MessageDto messageDto: messageDtos){
                messageList.messageList.add(SingleMessage.builder()
                        .messageId(messageDto.getMessageId())
                        .chatroomId(messageDto.getChatroomId())
                        .senderId(messageDto.getSenderId())
                        .content(messageDto.getContent())
                        .sendTime(String.valueOf(messageDto.getSendTime()))
                        .readTime((messageDto.getReadTime() == null) ? "읽지 않음" : "읽음")
                        .rendezvousFlag(messageDto.getRendezvousFlag())
                        .rendezvousLocation(messageDto.getRendezvousLocation())
                        .rendezvousTime((messageDto.getRendezvousTime() == null) ? null : String.valueOf(messageDto.getRendezvousTime()))
                        .build());
            }
            return messageList;
        }
    }

    @Data
    @Builder
    public static class SingleMessage {
        Long messageId;
        Long chatroomId;
        String senderId;
        String content;
        String sendTime;
        String readTime;
        Boolean rendezvousFlag;
        String rendezvousLocation;
        String rendezvousTime;




    }
}
