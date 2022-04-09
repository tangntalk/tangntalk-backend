package com.example.tangntalk.web.chatroom.util;

import com.example.tangntalk.web.account.dto.AccountDto;
import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import com.example.tangntalk.web.chatroom.dto.response.ChatroomDetailDto;
import com.example.tangntalk.web.message.dto.MessageDto;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public class RendezvousMasker {

    public static String maskRendezvous(MessageDto messageDto, AccountDto accountDto){

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if(messageDto.getRendezvousFlag() && !messageDto.getSenderId().equals(accountDto.getUsername())) {
            if (!messageDto.getRendezvousLocation().equals(accountDto.getAccountLocation()) || currentTime.after(messageDto.getRendezvousTime())) {
                messageDto.setContent("hidden message");
            }
        }
        return messageDto.getContent();
    }


    public static String maskRendezvous(ChatroomDetailDto chatroomDetail, AccountDto accountDto){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if(chatroomDetail.getRendezvousFlag() && !chatroomDetail.getSenderId().equals(accountDto.getUsername())) {
            if (!chatroomDetail.getRendezvousLocation().equals(accountDto.getAccountLocation()) || currentTime.after(chatroomDetail.getRendezvousTime())) {
                chatroomDetail.setContent("hidden message");
            }
        }
        return chatroomDetail.getContent();
    }
}
