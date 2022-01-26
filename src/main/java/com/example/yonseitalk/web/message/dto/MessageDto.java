package com.example.yonseitalk.web.message.dto;

import com.example.yonseitalk.web.chatroom.dao.Chatroom;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDto;
import com.example.yonseitalk.web.message.dao.Message;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dto.UserDto;
import lombok.*;

import java.util.Date;

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
                .senderId(message.getSender().getUserId())
                .content(message.getContent())
                .sendTime(message.getSendTime())
                .readTime(message.getReadTime())
                .rendezvousFlag(message.getRendezvousFlag())
                .rendezvousLocation(message.getRendezvousLocation())
                .rendezvousTime(message.getRendezvousTime())
                .build();
    }

}
