package com.example.tangntalk.web.chatroom.dto;

import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.chatroom.domain.Chatroom;
import com.example.tangntalk.web.message.dto.MessageDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class ChatroomDto {

    private Long chatroomId;
    private AccountDto user1;
    private AccountDto user2;
    private MessageDto lastMessage;

    public static ChatroomDto fromChatroom(Chatroom chatroom){
        return ChatroomDto.builder()
                .chatroomId(chatroom.getChatroomId())
                .user1(AccountDto.fromAccount(chatroom.getUser1()))
                .user2(AccountDto.fromAccount(chatroom.getUser2()))
                .lastMessage(MessageDto.fromMessage(chatroom.getLastMessage()))
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class ChatroomDetail {

        private Long chatroomId;
        private String account1Id;
        private String account2Id;
        private String account1Name;
        private String account2Name;
        private String senderId;
        private String content;
        private Date sendTime;
        private Boolean rendezvousFlag;
        private String rendezvousLocation;
        private Date rendezvousTime;
        private Boolean account1ConnectionStatus;
        private Boolean account2ConnectionStatus;

    }

    @Data
    public static class ChatroomList {
        List<SingleChatroom> chatrooms = new ArrayList<>();

        public static ChatroomList fromChatroomDetailList(List<ChatroomDto.ChatroomDetail> chatroomDetailList, String username){
            ChatroomList chatroomList = new ChatroomList();

            chatroomDetailList.forEach(chatroomDetail -> {
                boolean user1IsAccount = chatroomDetail.getAccount1Id().equals(username);
                chatroomList.chatrooms.add(SingleChatroom.builder()
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
            return chatroomList;
        }
    }


    @Data
    @Builder
    public static class SingleChatroom {
        Long chatroomId;
        String lastMessage;
        String lastSendTime;
        String lastMessageFrom;
        String opponentName;
        String opponentId;
        String messageLocation;
        String rendezvousTime;
        Boolean connectionStatus;

    }

}
