package com.example.tangntalk.web.message.dto.response;

import com.example.tangntalk.web.message.dto.MessageDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MessageListDto{
    List<SingleMessageDto> messageList = new ArrayList<>();

    public static MessageListDto fromMessageDtoList(List<MessageDto> messageDtos){
        MessageListDto messageList = new MessageListDto();
        messageList.messageList = messageDtos.stream().map(messageDto -> SingleMessageDto.from(messageDto)).collect(Collectors.toList());

        return messageList;
    }
}
