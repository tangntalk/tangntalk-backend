package com.example.yonseitalk.web.message.dto;

import com.example.yonseitalk.view.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageCount extends DefaultResponse {
    Long message_count;
}
