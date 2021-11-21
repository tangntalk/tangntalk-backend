package com.example.yonseitalk.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatroomWrapper {
    private Chatroom chatroom;
    private Message last_message;
}
