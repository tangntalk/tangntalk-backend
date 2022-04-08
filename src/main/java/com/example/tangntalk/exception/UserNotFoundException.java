package com.example.tangntalk.exception;

public class UserNotFoundException extends CommonException {
    public UserNotFoundException() {
        super();
        code = "NOT_FOUND";
    }
}