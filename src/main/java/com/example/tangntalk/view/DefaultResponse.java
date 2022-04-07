package com.example.tangntalk.view;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResponse {
    private boolean success;

    public DefaultResponse(){

    }

    public DefaultResponse(boolean success) {
        this.success = success;
    }
}
