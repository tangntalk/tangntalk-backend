package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.Chatroom;
import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.domain.nearbyUser;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.repository.ChatroomRepository;
import com.example.yonseitalk.repository.UserRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")

public class UserInfoController {

    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;

    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> userInfo(@PathVariable("user_id") String userId){

        Optional<User> user = userRepository.findById(userId);
        Map<String, String> userInfo = new HashMap<>();
        Map<String, Object> response = new HashMap<>();

        if(!user.isPresent()) {
            response.put("success", false);
            response.put("code", new NotFoundException());
        } else{
            response.put("success", true);
            userInfo.put("name", user.get().getName());
            userInfo.put("status_message", user.get().getStatus_message());
            userInfo.put("location_name", user.get().getUser_location());
            response.put("user", userInfo);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{user_id}/location")
    public ResponseEntity<?> updateLocation(@PathVariable("user_id") String userId, @RequestBody Map<String, String> location){

        int status = userRepository.updateUserLocation(userId, location.get("location_name"));

        Map<String, Object> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{user_id}/status")
    public ResponseEntity<?> updateMessage(@PathVariable("user_id") String userId, @RequestBody Map<String, String> statusMessage){

        int status = userRepository.updateStatusMessage(userId, statusMessage.get("status_message"));

        Map<String, Object> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") String userId){

        int status = userRepository.delete(userId);

        Map<String, Object> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //nearby
    @GetMapping(value = "/{user_id}/nearby")
    public ResponseEntity<?> nearbyUser(@PathVariable("user_id") String userId){

        Map<String, Object> response = new HashMap<>();

        Optional<User> user = userRepository.findById(userId);


        if(!user.isPresent()) {
            response.put("success", false);
            response.put("code", new NotFoundException());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        String location=user.get().getUser_location();
        response.put("success",true);
        response.put("myplace",location);
        //추가하기

        ArrayList<nearbyUser> onlineUser = new ArrayList<>();
        ArrayList<nearbyUser> offlineUser =new ArrayList<>();

        //ArrayList<User>
        List<User> nearbyPeople= userRepository.findByLocation(location);

        for(User user2: nearbyPeople){
            if(user2.getUser_id().equals(user.get().getUser_id())){
                continue;
            }
            if(user2.getConnection_status()){
                //connection
                nearbyUser onlineNearByUser= new nearbyUser();
                onlineNearByUser.setUser_id(user2.getUser_id());
                onlineNearByUser.setName(user2.getName());
                onlineNearByUser.setType(user2.getType());
                onlineNearByUser.setStatus_message(user2.getStatus_message());
                //chatroom 추가
                Optional<Chatroom> chatroom =chatroomRepository.findByPairUser(user.get().getUser_id(),user2.getUser_id());

                if (chatroom.isPresent()){
                    onlineNearByUser.setChatroom_id(chatroom.get().getChatroom_id());
                }

                onlineUser.add(onlineNearByUser);
            }
            else{//not connection
                nearbyUser offlineNearByUser= new nearbyUser();
                offlineNearByUser.setUser_id(user2.getUser_id());
                offlineNearByUser.setName(user2.getName());
                offlineNearByUser.setType(user2.getType());
                offlineNearByUser.setStatus_message(user2.getStatus_message());

                // chatroom
                Optional<Chatroom> chatroom =chatroomRepository.findByPairUser(user.get().getUser_id(),user2.getUser_id());

                if (chatroom.isPresent()){
                    offlineNearByUser.setChatroom_id(chatroom.get().getChatroom_id());
                }


                offlineUser.add(offlineNearByUser);
            }

        }
        response.put("online",onlineUser);
        response.put("offline",offlineUser);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
