package com.example.tangntalk.exception;

public class DuplicateAccountException extends CommonException{

    public DuplicateAccountException() {
        super();
        code = "ACCOUNT_DUPLICATED";
    }
}
