package com.example.yonseitalk.exception;

abstract public class CommonException extends RuntimeException {
    protected String code;

    public String getCode() {
        return code;
    }
}
