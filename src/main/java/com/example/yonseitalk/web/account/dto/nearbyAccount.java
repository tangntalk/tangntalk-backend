package com.example.yonseitalk.web.account.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class nearbyAccount {

    private String name;
    private String accountId;
    private String statusMessage;
    private String type;
    private Long chatroomId;
}
