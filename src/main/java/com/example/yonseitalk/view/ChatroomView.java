package com.example.yonseitalk.view;

import com.example.yonseitalk.view.DefaultResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ChatroomView {

    Long chatroom_id;
    String last_message;
    Timestamp last_send_time;
    String last_message_from;
    String opponent_name;
    String message_location;
    Timestamp rendezvous_time;

}
