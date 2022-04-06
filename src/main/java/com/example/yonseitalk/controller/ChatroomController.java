package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDto;
import com.example.yonseitalk.web.chatroom.service.ChatService;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.account.service.AccountService;
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

    @PostMapping(value = "/{account_id}/chatrooms")
    public Response.Item<Long> newChatroom(@PathVariable("account_id") String accountId, @RequestBody Map<String, String> body){
        return new Response.Item<>(chatService.addChatroom(accountId, body.get("opponent_id")));
    }

    @GetMapping(value = "/{account_id}/chatrooms")
    public Response.Item<ChatroomDto.ChatroomList> getChatroomList(@PathVariable("account_id") String accountId){
        return new Response.Item<>(chatService.createChatroomView(accountId));

    }

    @GetMapping(value = "/{account_id}/chatrooms/{chatroom_id}")
    public Response.Item<MessageDto.MessageList> getMessages(@PathVariable("account_id") String accountId, @PathVariable("chatroom_id") Long chatroomId){
        return new Response.Item<>(chatService.messageInquiry(chatroomId, accountId));
    }

    @PostMapping(value = "/{account_id}/chatrooms/{chatroom_id}")
    public Response.Item<Map<String, Boolean>> postMessage(@PathVariable("account_id") String accountId, @PathVariable("chatroom_id") Long chatroomId, @RequestBody Map<String, String> body){

        Long rendezvous_time = (body.get("rendezvous_time").equals(null)) ? -1L : Long.valueOf(body.get("rendezvous_time"));
        Long status = chatService.sendMessage(accountId, chatroomId, String.valueOf(body.get("content")), rendezvous_time);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", status > -1? true: false);
        return new Response.Item<>(response);
    }

    @GetMapping(value = "/{account_id}/chatrooms/{chatroom_id}/count")
    public Response.Item<MessageDto.MessageCount> getMessageCount(@PathVariable("account_id") String accountId, @PathVariable("chatroom_id") Long chatroomId){
        MessageDto.MessageCount messageCount = new MessageDto.MessageCount(chatService.getMessageCount(chatroomId));
        return new Response.Item<>(messageCount);
    }
}
