package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.message.dto.MessageDto;
import com.example.tangntalk.web.account.service.AccountService;
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
    public Response.Item<ChatroomDto.ChatroomList> getChatroomList(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(chatService.createChatroomView(username));

    }

    @GetMapping(value = "/chatrooms/{chatroomId}")
    public Response.Item<MessageDto.MessageList> getMessages(@PathVariable("chatroomId") Long chatroomId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Response.Item<>(chatService.messageInquiry(chatroomId, username));
    }

    @PostMapping(value = "/chatrooms/{chatroomId}")
    public Response.Item<Map<String, Boolean>> postMessage(@PathVariable("chatroomId") Long chatroomId, @RequestBody Map<String, String> body){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Long rendezvousTime = (body.get("rendezvousTime").equals(null)) ? -1L : Long.valueOf(body.get("rendezvousTime"));
        Long status = chatService.sendMessage(username, chatroomId, String.valueOf(body.get("content")), rendezvousTime);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", status > -1? true: false);
        return new Response.Item<>(response);
    }

    @GetMapping(value = "/chatrooms/{chatroomId}/count")
    public Response.Item<MessageDto.MessageCount> getMessageCount(@PathVariable("chatroomId") Long chatroomId){
        MessageDto.MessageCount messageCount = new MessageDto.MessageCount(chatService.getMessageCount(chatroomId));
        return new Response.Item<>(messageCount);
    }
}
