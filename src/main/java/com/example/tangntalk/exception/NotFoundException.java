package com.example.tangntalk.exception;

public class NotFoundException extends CommonException {
    public NotFoundException() {
        super();
        code = "NOT_FOUND";
    }
}
