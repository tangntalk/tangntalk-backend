package com.example.yonseitalk.web.chatroom.dto;

import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.account.dto.AccountDtoTemp;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ChatroomDto {

    private Long chatroomId;
    private AccountDtoTemp user1;
    private AccountDtoTemp user2;
    private MessageDto lastMessage;

    public static ChatroomDto fromChatroom(Chatroom chatroom){
        return ChatroomDto.builder()
                .chatroomId(chatroom.getChatroomId())
                .user1(AccountDtoTemp.fromAccount(chatroom.getUser1()))
                .user2(AccountDtoTemp.fromAccount(chatroom.getUser2()))
                .lastMessage(MessageDto.fromMessage(chatroom.getLastMessage()))
                .build();
    }

}
