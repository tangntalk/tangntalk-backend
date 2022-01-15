package com.example.yonseitalk.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.domain.User;
import com.example.yonseitalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    public User login(String loginId, String password){
        User loginUser= userRepository.findById(loginId).filter(m -> AES128.getAES128_Decode(URLDecoder.decode(m.getPassword(), StandardCharsets.UTF_8)).equals(password)).orElse(null);
        log.info("user: {}",loginUser);
        return loginUser;
//        return userRepository.findById(loginId).get();
    }

    @Transactional
    public void updateConnectionTrue(User user){
        userRepository.updateUserConnectionStatus(user.getUser_id(),true);
    }

    @Transactional
    public void updateConnectionFalse(User user){
        userRepository.updateUserConnectionStatus(user.getUser_id(),false);
    }

}
