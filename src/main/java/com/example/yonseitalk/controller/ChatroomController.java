package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.chatroom.dto.ChatroomDto;
import com.example.yonseitalk.web.chatroom.service.ChatService;
import com.example.yonseitalk.web.message.dto.MessageDto;
import com.example.yonseitalk.web.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatroomController {

    private final ChatService chatService;
    private final AccountService accountService;

    @PostMapping(value = "/{user_id}/chatrooms")
    public Response.Item<Long> newChatroom(@PathVariable("user_id") String userId, @RequestBody Map<String, String> body){
        return new Response.Item<>(chatService.addChatroom(userId, body.get("opponent_id")));
    }

    @GetMapping(value = "/{user_id}/chatrooms")
    public Response.ItemList<ChatroomDto.SingleChatroom> getChatroomList(@PathVariable("user_id") String userId){

        return new Response.ItemList<>(chatService.createChatroomView(userId));

    }

    @GetMapping(value = "/{user_id}/chatrooms/{chatroom_id}")
    public Response.ItemList<MessageDto.SingleMessage> getMessages(@PathVariable("user_id") String userId, @PathVariable("chatroom_id") Long chatroomId){
        return new Response.ItemList<>(chatService.messageInquiry(chatroomId, userId));
    }

    @PostMapping(value = "/{user_id}/chatrooms/{chatroom_id}")
    public Response.Item<Map<String, Boolean>> postMessage(@PathVariable("user_id") String userId, @PathVariable("chatroom_id") Long chatroomId, @RequestBody Map<String, String> body){

        Long rendezvous_time = (body.get("rendezvous_time").equals(null)) ? -1L : Long.valueOf(body.get("rendezvous_time"));
        Long status = chatService.sendMessage(userId, chatroomId, String.valueOf(body.get("content")), rendezvous_time);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", status > -1? true: false);
        return new Response.Item<>(response);
    }

    @GetMapping(value = "/{user_id}/chatrooms/{chatroom_id}/count")
    public Response.Item<MessageDto.MessageCount> getMessageCount(@PathVariable("user_id") String userId, @PathVariable("chatroom_id") Long chatroomId){
        MessageDto.MessageCount messageCount = new MessageDto.MessageCount(chatService.getMessageCount(chatroomId));
        return new Response.Item<>(messageCount);
    }
}
