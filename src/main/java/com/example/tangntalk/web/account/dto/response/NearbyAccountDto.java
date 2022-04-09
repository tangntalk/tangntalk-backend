package com.example.tangntalk.web.account.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NearbyAccountDto {

    private String name;
    private String username;
    private String statusMessage;
    private String type;
    private Long chatroomId;
}
