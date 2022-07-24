package com.example.tangntalk.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class GlobalException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final ExceptionResponse body;

    public GlobalException(HttpStatus httpStatus, ErrorType errorType) {
        super(errorType.getMessage());
        this.httpStatus = httpStatus;
        this.body = new ExceptionResponse(errorType.getErrorCode(), errorType.getMessage());
    }
}

