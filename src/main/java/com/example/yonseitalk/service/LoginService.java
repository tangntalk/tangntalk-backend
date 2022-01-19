package com.example.yonseitalk.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;

    public User login(String loginId, String password){

        return userService.findById(loginId).filter(m -> AES128.getAES128_Decode(URLDecoder.decode(m.getPassword(), StandardCharsets.UTF_8)).equals(password)).orElse(null);
//        return userRepository.findById(loginId).get();
    }

    @Transactional
    public void updateConnectionTrue(User user){
        userService.updateUserConnectionStatus(user.getUser_id(),true);
    }

    @Transactional
    public void updateConnectionFalse(User user){
        userService.updateUserConnectionStatus(user.getUser_id(),false);
    }

}
