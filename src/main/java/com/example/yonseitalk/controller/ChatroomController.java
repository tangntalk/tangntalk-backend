package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.ChatroomDetail;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.service.ChatService;
import com.example.yonseitalk.view.ChatroomView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatroomController {

    private final ChatService chatService;

    @PostMapping(value = "/{user_id}/chatrooms")
    public ResponseEntity<?> newChatroom(@PathVariable("user_id") String userId, @RequestBody Map<String, String> body){
        Long status = chatService.addChatroom(userId, body.get("opponent_id"));

        Map<String, Long> response = new HashMap<>();
        if(status >-1)
            response.put("chatroom_id", status);
        else
            response.put("success", -1L);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{user_id}/chatrooms")
    public ResponseEntity<?> getChatroomList(@PathVariable("user_id") String userId){
        List<ChatroomView> chatroomViews = chatService.findChatroom(userId);
        return new ResponseEntity<>(chatroomViews, HttpStatus.OK);
    }

    @GetMapping(value = "/{user_id}/chatrooms/{chatroom_id}")
    public ResponseEntity<?> getMessages(@PathVariable("user_id") String userId, @PathVariable("chatroom_id") Long chatroomId){
        List<Message> messages = chatService.messageInquiry(chatroomId, userId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping(value = "/{user_id}/chatrooms/{chatroom_id}")
    public ResponseEntity<?> postMessage(@PathVariable("user_id") String userId, @PathVariable("chatroom_id") Long chatroomId,
                                        @RequestBody Map<String, String> body){
        Long rendezvous_time = (body.get("rendezvous_time").equals(null)) ? -1L : Long.valueOf(body.get("rendezvous_time"));
        Long status = chatService.sendMessage(userId, chatroomId, String.valueOf(body.get("content")), rendezvous_time);

        Map<String, Boolean> response = new HashMap<>();
        if(status >-1)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
