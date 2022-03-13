package com.example.yonseitalk.web.chatroom.dto;

import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.account.dto.AccountDtoTemp;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
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

    @Getter
    @Setter
    @Builder
    @ToString
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



    @Getter
    @Setter
    @Builder
    @ToString
    public static class SingleChatroom {
        Long chatroomId;
        String lastMessage;
        String lastSendTime;
        String lastMEssageFrom;
        String opponentName;
        String opponentId;
        String messageLocation;
        String rendezvousTime;
        Boolean connectionStatus;

        public static List<SingleChatroom> fromChatroomDetailList(List<ChatroomDto.ChatroomDetail> chatroomDetailList, String accountId){
            List<SingleChatroom> singleChatrooms = new ArrayList<>();
            chatroomDetailList.forEach(chatroomDetail -> {
                boolean user1IsAccount = chatroomDetail.getAccount1Id().equals(accountId);
                singleChatrooms.add(SingleChatroom.builder()
                        .chatroomId(chatroomDetail.getChatroomId())
                        .lastMessage(chatroomDetail.getContent())
                        .lastSendTime(String.valueOf(chatroomDetail.getSendTime()))
                        .lastMEssageFrom(chatroomDetail.getSenderId())
                        .opponentName( user1IsAccount ? chatroomDetail.getAccount2Name() : chatroomDetail.getAccount1Name())
                        .opponentId( user1IsAccount ? chatroomDetail.getAccount2Id() : chatroomDetail.getAccount1Id())
                        .messageLocation(chatroomDetail.getRendezvousLocation())
                        .rendezvousTime(String.valueOf(chatroomDetail.getRendezvousTime()))
                        .connectionStatus( user1IsAccount ? chatroomDetail.getAccount2ConnectionStatus() : chatroomDetail.getAccount2ConnectionStatus())
                        .build());
            });
            return singleChatrooms;
        }
    }

}
