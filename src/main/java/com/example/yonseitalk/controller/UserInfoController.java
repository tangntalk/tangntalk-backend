package com.example.yonseitalk.controller;

import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.exception.NotFoundException;
import com.example.yonseitalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserRepository userRepository;

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

        Map<String, String> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/{user_id}/status")
    public ResponseEntity<?> updateMessage(@PathVariable("user_id") String userId, @RequestBody Map<String, String> statusMessage){

        int status = userRepository.updateStatusMessage(userId, statusMessage.get("status_message"));

        Map<String, String> response = new HashMap<>();
        if(status > 0)
            response.put("success", true);
        else
            response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
