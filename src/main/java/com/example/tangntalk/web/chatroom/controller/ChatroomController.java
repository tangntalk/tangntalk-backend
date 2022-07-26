package com.example.tangntalk.web.chatroom.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.chatroom.dto.response.ChatroomListDto;
import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.account.service.AccountService;
import com.example.tangntalk.web.message.dto.response.MessageCountDto;
import com.example.tangntalk.web.message.dto.response.MessageListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatService chatService;
    private final AccountService accountService;

    @PostMapping(value = "/chatrooms")
    public Response.Item<Long> newChatroom(@RequestBody Map<String, String> body){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(chatService.addChatroom(username, body.get("opponentUsername")));
    }

    @GetMapping(value = "/chatrooms")
    public Response.Item<ChatroomListDto> getChatroomList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(chatService.createChatroomView(username));
    }

    @GetMapping(value = "/chatrooms/{chatroomId}")
    public Response.Item<MessageListDto> getMessages(@PathVariable("chatroomId") Long chatroomId, int page){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(chatService.messageInquiry(username, chatroomId, page));
    }

    @GetMapping(value = "/chatrooms/{chatroomId}/count")
    public Response.Item<MessageCountDto> getMessageCount(@PathVariable("chatroomId") Long chatroomId){
        MessageCountDto messageCount = new MessageCountDto(chatService.getMessageCount(chatroomId));
        return new Response.Item<>(messageCount);
    }
}
