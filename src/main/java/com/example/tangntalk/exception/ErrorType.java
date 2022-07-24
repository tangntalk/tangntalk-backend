package com.example.tangntalk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    DUPLICATED_ACCOUNT_ERROR("409", "중복된 닉네임입니다."),
    NOT_FOUND_ERROR("404","존재하지 않습니다");

    private final String errorCode;
    private final String message;
}
