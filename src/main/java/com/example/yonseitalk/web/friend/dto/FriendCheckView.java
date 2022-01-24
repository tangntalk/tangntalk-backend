package com.example.yonseitalk.web.friend.dto;

import com.example.yonseitalk.view.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendCheckView extends DefaultResponse {

    boolean is_friend;
}
