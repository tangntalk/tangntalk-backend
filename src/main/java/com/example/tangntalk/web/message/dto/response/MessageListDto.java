package com.example.tangntalk.web.message.dto.response;

import com.example.tangntalk.web.message.dto.MessageDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageListDto{
    List<SingleMessageDto> messageList = new ArrayList<>();

    public static MessageListDto fromMessageDtoList(List<MessageDto> messageDtos){
        MessageListDto messageList = new MessageListDto();

        for(MessageDto messageDto: messageDtos){
            messageList.messageList.add(SingleMessageDto.builder()
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
