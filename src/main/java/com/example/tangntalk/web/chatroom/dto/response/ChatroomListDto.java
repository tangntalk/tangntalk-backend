package com.example.tangntalk.web.chatroom.dto.response;

import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class ChatroomListDto {
    List<ChatroomDto.SingleChatroom> chatrooms = new ArrayList<>();

    public static ChatroomListDto fromChatroomDetailList(List<ChatroomDetailDto> chatroomDetailList, String username){
        ChatroomListDto chatroomListDto = new ChatroomListDto();

        chatroomDetailList.forEach(chatroomDetail -> {
            boolean user1IsAccount = chatroomDetail.getAccount1Id().equals(username);
            chatroomListDto.chatrooms.add(ChatroomDto.SingleChatroom.builder()
                    .chatroomId(chatroomDetail.getChatroomId())
                    .lastMessage(chatroomDetail.getContent())
                    .lastSendTime(String.valueOf(chatroomDetail.getSendTime()))
                    .lastMessageFrom(chatroomDetail.getSenderId())
                    .opponentName( user1IsAccount ? chatroomDetail.getAccount2Name() : chatroomDetail.getAccount1Name())
                    .opponentId( user1IsAccount ? chatroomDetail.getAccount2Id() : chatroomDetail.getAccount1Id())
                    .messageLocation(chatroomDetail.getRendezvousLocation())
                    .rendezvousTime(String.valueOf(chatroomDetail.getRendezvousTime()))
                    .connectionStatus( user1IsAccount ? chatroomDetail.getAccount2ConnectionStatus() : chatroomDetail.getAccount2ConnectionStatus())
                    .build());
        });
        return chatroomListDto;
    }
}
