package com.example.tangntalk.controller;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.web.chatroom.dto.ChatroomDto;
import com.example.tangntalk.web.chatroom.service.ChatService;
import com.example.tangntalk.web.message.dto.MessageDto;
import com.example.tangntalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/accounts")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatroomController {

    private final ChatService chatService;
    private final AccountService accountService;

    @PostMapping(value = "/{accountId}/chatrooms")
    public Response.Item<Long> newChatroom(@PathVariable("accountId") String accountId, @RequestBody Map<String, String> body){
        return new Response.Item<>(chatService.addChatroom(accountId, body.get("opponentId")));
    }

    @GetMapping(value = "/{accountId}/chatrooms")
    public Response.Item<ChatroomDto.ChatroomList> getChatroomList(@PathVariable("accountId") String accountId){
        return new Response.Item<>(chatService.createChatroomView(accountId));

    }

    @GetMapping(value = "/{accountId}/chatrooms/{chatroomId}")
    public Response.Item<MessageDto.MessageList> getMessages(@PathVariable("accountId") String accountId, @PathVariable("chatroomId") Long chatroomId){
        return new Response.Item<>(chatService.messageInquiry(chatroomId, accountId));
    }

    @PostMapping(value = "/{accountId}/chatrooms/{chatroomId}")
    public Response.Item<Map<String, Boolean>> postMessage(@PathVariable("accountId") String accountId, @PathVariable("chatroomId") Long chatroomId, @RequestBody Map<String, String> body){

        Long rendezvousTime = (body.get("rendezvousTime").equals(null)) ? -1L : Long.valueOf(body.get("rendezvousTime"));
        Long status = chatService.sendMessage(accountId, chatroomId, String.valueOf(body.get("content")), rendezvousTime);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", status > -1? true: false);
        return new Response.Item<>(response);
    }

    @GetMapping(value = "/{accountId}/chatrooms/{chatroomId}/count")
    public Response.Item<MessageDto.MessageCount> getMessageCount(@PathVariable("accountId") String accountId, @PathVariable("chatroomId") Long chatroomId){
        MessageDto.MessageCount messageCount = new MessageDto.MessageCount(chatService.getMessageCount(chatroomId));
        return new Response.Item<>(messageCount);
    }
}
