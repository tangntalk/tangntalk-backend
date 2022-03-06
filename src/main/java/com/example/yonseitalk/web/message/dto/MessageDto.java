package com.example.yonseitalk.web.message.dto;

import com.example.yonseitalk.web.message.domain.Message;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
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
                .senderId(message.getSender().getAccountId())
                .content(message.getContent())
                .sendTime(message.getSendTime())
                .readTime(message.getReadTime())
                .rendezvousFlag(message.getRendezvousFlag())
                .rendezvousLocation(message.getRendezvousLocation())
                .rendezvousTime(message.getRendezvousTime())
                .build();
    }

    // TODO: toMessage()를 만들어야한다.
    @AllArgsConstructor
    public static class MessageCount {
        Long message_count;
    }

    @Getter
    @Setter
    @Builder
    public static class SingleMessage {
        Long message_id;
        Long chatroom_id;
        String sender_id;
        String content;
        String send_time;
        String read_time;
        Boolean rendezvous_flag;
        String rendezvous_location;
        String rendezvous_time;



        public static List<SingleMessage> fromMessageDtoList(List<MessageDto> messageDtos){
            List<SingleMessage> messages = new ArrayList<>();

            for(MessageDto messageDto: messageDtos){
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
            return messages;
        }
    }
}
