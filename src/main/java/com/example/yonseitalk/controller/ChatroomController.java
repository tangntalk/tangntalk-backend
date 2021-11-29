package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.ChatroomDetail;
import com.example.yonseitalk.domain.Message;
import com.example.yonseitalk.repository.UserRepository;
import com.example.yonseitalk.service.ChatService;
import com.example.yonseitalk.view.chatroom.ChatroomView;
import com.example.yonseitalk.view.chatroom.MessageListView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatroomController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @PostMapping(value = "/{user_id}/chatrooms")
    public ResponseEntity<?> newChatroom(@PathVariable("user_id") String userId, @RequestBody Map<String, String> body){
        Long status = chatService.addChatroom(userId, body.get("opponent_id"));

        Map<String, Long> response = new HashMap<>();
        if(status >-1L)
            response.put("chatroom_id", status);
        else
            response.put("success", -1L);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{user_id}/chatrooms")
    public ChatroomView getChatroomList(@PathVariable("user_id") String userId){
        List<ChatroomDetail> chatroomDetails = chatService.findChatroom(userId);
        ChatroomView chatroomView = new ChatroomView();
        if(!chatroomDetails.isEmpty()){
            chatroomDetails.forEach(chatroomDetail -> {
                String opponentId = (chatroomDetail.getUser_1().equals(userId))? chatroomDetail.getUser_2() : chatroomDetail.getUser_1();
                chatroomDetail.setUser_1(userRepository.findById(chatroomDetail.getUser_1()).get().getName());
                chatroomDetail.setUser_2(userRepository.findById(chatroomDetail.getUser_2()).get().getName());
               chatroomView.addSingleChatroom(chatroomDetail, userRepository.findById(userId).get().getName(), opponentId);
            });
        }
        chatroomView.setSuccess(true);
        return chatroomView;
    }

    @GetMapping(value = "/{user_id}/chatrooms/{chatroom_id}")
    public MessageListView getMessages(@PathVariable("user_id") String userId, @PathVariable("chatroom_id") Long chatroomId){
        List<Message> messageList = chatService.messageInquiry(chatroomId, userId);
        MessageListView messageListView = new MessageListView();
        if(!messageList.isEmpty()) {
            for(Message m: messageList)
                messageListView.addSingleMessage(m);
        }
        messageListView.setSuccess(true);
        return messageListView;
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
