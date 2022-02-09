package com.example.yonseitalk.controller;

import com.example.yonseitalk.common.dto.Response;
import com.example.yonseitalk.web.chatroom.domain.Chatroom;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.dto.UserInfoQueryResponse;
import com.example.yonseitalk.web.user.dto.nearbyUser;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.web.chatroom.domain.ChatroomRepository;
import com.example.yonseitalk.web.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserInfoController {

    private final UserService userService;
    private final ChatroomRepository chatroomRepository;

    @GetMapping(value = "/{user_id}")
    public Response.Item<UserInfoQueryResponse> userInfo(@PathVariable("user_id") String userId){
        return new Response.Item<>(userService.userInfoQuery(userId));
    }

    @PostMapping(value = "/{user_id}/location")
    public ResponseEntity<?> updateLocation(@PathVariable("user_id") String userId, @RequestBody Map<String, String> location){

        int status = userService.updateUserLocation(userId, location.get("location_name"));

        Map<String, Object> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{user_id}/status")
    public ResponseEntity<?> updateMessage(@PathVariable("user_id") String userId, @RequestBody Map<String, String> statusMessage){

        int status = userService.updateStatusMessage(userId, statusMessage.get("status_message"));

        Map<String, Object> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user_id") String userId){

        userService.deleteById(userId);

        Map<String, Object> response = new HashMap<>();
//        if(status > 0)
        response.put("success", true);
//        else
//            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //nearby
    @GetMapping(value = "/{user_id}/nearby/{target_location}")
    public ResponseEntity<?> nearbyUser(@PathVariable("user_id") String userId,@PathVariable("target_location") String target_location){

        Map<String, Object> response = new HashMap<>();

        Optional<UserDto> userDto = userService.findById(userId);


        if(!userDto.isPresent()) {
            response.put("success", false);
            response.put("code", new NotFoundException());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        String location=userDto.get().getUserLocation();
        response.put("success",true);
        response.put("myplace",location);
        //추가하기

        ArrayList<nearbyUser> onlineUser = new ArrayList<>();
        ArrayList<nearbyUser> offlineUser =new ArrayList<>();

        //ArrayList<User>
        List<UserDto> nearbyPeople = userService.findByLocation(target_location);

        for(UserDto user2: nearbyPeople){
            if(user2.getUserId().equals(userDto.get().getUserId())){
                continue;
            }
            if(user2.getConnectionStatus()){
                //connection
                nearbyUser onlineNearByUser= new nearbyUser();
                onlineNearByUser.setUserId(user2.getUserId());
                onlineNearByUser.setName(user2.getName());
                onlineNearByUser.setType(user2.getType());
                onlineNearByUser.setStatusMessage(user2.getStatusMessage());
                //chatroom 추가
                Optional<Chatroom> chatroom =chatroomRepository.findByPairUser(userDto.get().getUserId(),user2.getUserId());

                if (chatroom.isPresent()){
                    onlineNearByUser.setChatroomId(chatroom.get().getChatroomId());
                }

                onlineUser.add(onlineNearByUser);
            }
            else{//not connection
                nearbyUser offlineNearByUser= new nearbyUser();
                offlineNearByUser.setUserId(user2.getUserId());
                offlineNearByUser.setName(user2.getName());
                offlineNearByUser.setType(user2.getType());
                offlineNearByUser.setStatusMessage(user2.getStatusMessage());

                // chatroom
                Optional<Chatroom> chatroom =chatroomRepository.findByPairUser(userDto.get().getUserId(),user2.getUserId());

                if (chatroom.isPresent()){
                    offlineNearByUser.setChatroomId(chatroom.get().getChatroomId());
                }


                offlineUser.add(offlineNearByUser);
            }

        }
        response.put("online",onlineUser);
        response.put("offline",offlineUser);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
