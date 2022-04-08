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

    @PostMapping(value = "/{username}/chatrooms")
    public Response.Item<Long> newChatroom(@PathVariable("username") String username, @RequestBody Map<String, String> body){
        return new Response.Item<>(chatService.addChatroom(username, body.get("opponentId")));
    }

    @GetMapping(value = "/{username}/chatrooms")
    public Response.Item<ChatroomDto.ChatroomList> getChatroomList(@PathVariable("username") String username){
        return new Response.Item<>(chatService.createChatroomView(username));

    }

    @GetMapping(value = "/{username}/chatrooms/{chatroomId}")
    public Response.Item<MessageDto.MessageList> getMessages(@PathVariable("username") String username, @PathVariable("chatroomId") Long chatroomId){
        return new Response.Item<>(chatService.messageInquiry(chatroomId, username));
    }

    @PostMapping(value = "/{username}/chatrooms/{chatroomId}")
    public Response.Item<Map<String, Boolean>> postMessage(@PathVariable("username") String username, @PathVariable("chatroomId") Long chatroomId, @RequestBody Map<String, String> body){

        Long rendezvousTime = (body.get("rendezvousTime").equals(null)) ? -1L : Long.valueOf(body.get("rendezvousTime"));
        Long status = chatService.sendMessage(username, chatroomId, String.valueOf(body.get("content")), rendezvousTime);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", status > -1? true: false);
        return new Response.Item<>(response);
    }

    @GetMapping(value = "/{username}/chatrooms/{chatroomId}/count")
    public Response.Item<MessageDto.MessageCount> getMessageCount(@PathVariable("username") String username, @PathVariable("chatroomId") Long chatroomId){
        MessageDto.MessageCount messageCount = new MessageDto.MessageCount(chatService.getMessageCount(chatroomId));
        return new Response.Item<>(messageCount);
    }
}
